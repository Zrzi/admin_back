package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.DataMapper;
import com.admin.admin_back.mapper.TaskErrorMapper;
import com.admin.admin_back.mapper.TaskMapper;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.*;
import com.admin.admin_back.pojo.event.ExcelEvent;
import com.admin.admin_back.service.ExcelHelper;
import com.admin.admin_back.service.LogTask;
import com.admin.admin_back.utils.EventPublisher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈群矜
 */
@Service
public class ExcelHelperService implements ExcelHelper {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskErrorMapper taskErrorMapper;

    @Autowired
    private LogTask logTask;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    @Async("uploadExcelExecutor")
    @Transactional(rollbackFor = RuntimeException.class)
    public void batchSave(String taskCode,
                          ExcelDto excelDto,
                          List<ExcelDataDto> dataList,
                          List<List<String>> uniqueKeys,
                          boolean isCover,
                          String userNo) {
        TaskErrorDto taskErrorDto = new TaskErrorDto();
        taskErrorDto.setTaskId(taskCode);
        taskErrorDto.setCreatedBy(userNo);
        boolean isSuccess = true;
        int size = dataList.size();
        int insert = 0;
        int update = 0;
        for (int i=0; i<size; ++i) {
            ExcelDataDto excelDataDto = dataList.get(i);
            Map<String, Object> primaryKeys = excelDataDto.getPrimaryKeys();
            Map<String, Object> data = excelDataDto.getData();
            String flag = checkIfExistDuplicate(excelDto, excelDataDto, uniqueKeys);
            boolean exist = StringUtils.isNotBlank(flag);
            DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
            try {
                if (exist) {
                    if (isCover) {
                        // 存在数据，设置为覆盖
                        updateDate(excelDto, data, primaryKeys, uniqueKeys);
                        eventPublisher.publishEvent(new ExcelEvent(this, excelDto.getSqlName(), data, userNo, true));
                        ++update;
                    } else {
                        // 存在数据，设置为不覆盖
                        isSuccess = false;
                        taskErrorDto.setErrorMessage("第" + (i + 1) + "条数据，" + flag);
                        taskErrorMapper.insertTaskError(taskErrorDto);
                        logTask.logInfo(taskErrorDto.getErrorMessage());
                    }
                } else {
                    // 不存在数据，直接插入
                    LinkedList<String> keys = new LinkedList<>();
                    LinkedList<Object> values = new LinkedList<>();
                    data.forEach((key, value) -> {
                        keys.addLast(key);
                        values.addLast(value);
                    });
                    insertData(excelDto, keys, values);
                    eventPublisher.publishEvent(new ExcelEvent(this, excelDto.getSqlName(), data, userNo, false));
                    ++insert;
                }
                transactionManager.commit(transactionStatus);
            } catch (Exception exception) {
                isSuccess = false;
                transactionManager.rollback(transactionStatus);
                taskErrorDto.setErrorMessage("第" + (i + 1) + "条数据，" + (exist ? "更新" : "插入") + "出现服务器异常。");
                taskErrorMapper.insertTaskError(taskErrorDto);
                logTask.logInfo(exception.getMessage());
            }
        }
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(taskCode);
        taskDto.setTaskStatus(isSuccess ? Constant.TASK_SUCCESS : Constant.TASK_ERROR);
        taskDto.setTaskSuccessInsert(insert);
        taskDto.setTaskSuccessUpdate(update);
        taskDto.setUpdatedBy(userNo);
        taskMapper.updateTask(taskDto);
    }

    /**
     * 检查数据重复
     * @param excelDto 用于传入sqlName
     * @param data 传入数据等
     * @param uniqueKeys 唯一约束的列表
     * @return 字符串：错误信息，如果为空，则表示不存在重复数据
     */
    private String checkIfExistDuplicate(ExcelDto excelDto, ExcelDataDto data, List<List<String>> uniqueKeys) {
        // 可能时没有主键，也可能是主键自增，所以 primaryKeys为空
        StringBuilder builder = new StringBuilder();
        Map<String, Object> primaryKeys = data.getPrimaryKeys();
        if (!CollectionUtils.isEmpty(primaryKeys)) {
            if (findDataByPrimaryKeys(excelDto, primaryKeys)) {
                // {excelName}中，与数据库{sqlName}中的数据在{[a,b,c]}字段重复
                builder.append(excelDto.getExcelName()).append("中，与数据库").append(excelDto.getSqlName()).append("中的数据在");
                builder.append(primaryKeys.keySet().stream().collect(Collectors.joining(", ", "[", "]")));
                builder.append("字段重复。");
                return builder.toString();
            }
        }
        Map<String, Object> unique = new HashMap<>();
        Map<String, Object> dataMap  = data.getData();
        for (List<String> keys : uniqueKeys) {
            if (!CollectionUtils.isEmpty(keys)) {
                // 构筑unique
                for (String key : keys) {
                    unique.put(key, dataMap.get(key));
                }
                if (findDataByUniqueKeys(excelDto, unique)) {
                    builder.append(excelDto.getExcelName()).append("中，与数据库").append(excelDto.getSqlName()).append("中的数据在");
                    builder.append(keys.stream().collect(Collectors.joining(", ", "[", "]")));
                    builder.append("字段重复。");
                    return builder.toString();
                }
                unique.clear();
            }
        }
        return builder.toString();
    }

    /**
     * 根据主键查询数据库中是否存在相关数据
     * @param excelDto 用于传入sqlName
     * @param primaryKeys 主键及对应值
     * @return true代表存在；false代表不存在
     */
    private boolean findDataByPrimaryKeys(ExcelDto excelDto, Map<String, Object> primaryKeys) {
        return dataMapper.selectCountDataByPrimaryKeys(excelDto, primaryKeys) != 0;
    }

    /**
     * 根据唯一键查询数据库中是否存在相关数据
     * @param excelDto 用于传入sqlName
     * @param uniqueKeys 唯一键及对应值
     * @return true代表存在；false代表不存在
     */
    private boolean findDataByUniqueKeys(ExcelDto excelDto, Map<String, Object> uniqueKeys) {
        return dataMapper.selectCountDataByUniqueKeys(excelDto, uniqueKeys) != 0;
    }

    private void insertData(ExcelDto excelDto, List<String> keys, List<Object> values) {
        dataMapper.insertData(excelDto, keys, values);
    }

    /**
     * 更新数据。
     * 如果存在主键，将根据主键更新数据；否则根据唯一键更新。
     * 主键缺失原因：没有设置主键；主键自增。
     * 如果唯一键也不存在，{@code checkIfExistDuplicate}保证返回{@code false}
     * 举例：
     *  数据库字段为a,b,c,d,e,f，其中，a为主键，b、(c,d)存在唯一约束
     *  如果a不是自增字段，将根据a进行更新
     *  反之，将根据(b,c,d)进行更新
     * @param excelDto 用于传入sqlName
     * @param data 数据，所有字段
     * @param primaryKeys 数据，仅主键
     * @param uniqueKeyList 唯一约束列表
     */
    private void updateDate(ExcelDto excelDto,
                            Map<String, Object> data,
                            Map<String, Object> primaryKeys,
                            List<List<String>> uniqueKeyList) {
        if (CollectionUtils.isEmpty(primaryKeys)) {
            Map<String, Object> uniqueKeys = new HashMap<>();
            for (List<String> keys : uniqueKeyList) {
                for (String key : keys) {
                    uniqueKeys.put(key, data.get(key));
                }
            }
            dataMapper.updateData(excelDto, data, uniqueKeys);
        } else {
            dataMapper.updateData(excelDto, data, primaryKeys);
        }
    }

//    @Override
//    @Async("uploadExcelExecutor")
//    @Transactional(rollbackFor = RuntimeException.class)
//    public void testAsync(String code) {
//        TaskDto taskDto = new TaskDto();
//        taskDto.setTaskId(code);
//        taskDto.setTaskStatus(Constant.TASK_SUCCESS);
//        // 模拟异常请情况
//        int i = 1 / 0;
//        taskMapper.updateTask(taskDto);
//    }
//
//    @Override
//    @Async("uploadExcelExecutor")
//    public void testExecutor(int i) {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(i);
//    }

}
