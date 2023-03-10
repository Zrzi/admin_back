package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.DataMapper;
import com.admin.admin_back.mapper.TaskErrorMapper;
import com.admin.admin_back.mapper.TaskMapper;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.ExcelDataDto;
import com.admin.admin_back.pojo.dto.ExcelDto;
import com.admin.admin_back.pojo.dto.TaskDto;
import com.admin.admin_back.pojo.dto.TaskErrorDto;
import com.admin.admin_back.pojo.event.ExcelEvent;
import com.admin.admin_back.service.ExcelHelper;
import com.admin.admin_back.service.LogTask;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 陈群矜
 */
@Service
public class ExcelHelperService implements ExcelHelper, ApplicationContextAware {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskErrorMapper taskErrorMapper;

    @Autowired
    private LogTask logTask;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    @Async("uploadExcelExecutor")
    @Transactional(rollbackFor = RuntimeException.class)
    public void batchSave(String taskCode, ExcelDto excelDto, List<ExcelDataDto> dataList, boolean isCover, String userNo) {
        TaskErrorDto taskErrorDto = new TaskErrorDto();
        taskErrorDto.setTaskId(taskCode);
        taskErrorDto.setCreatedBy(userNo);
        boolean isSuccess = true;
        int size = dataList.size();
        for (int i=0; i<size; ++i) {
            ExcelDataDto excelDataDto = dataList.get(i);
            Map<String, Object> primaryKeys = excelDataDto.getPrimaryKeys();
            Map<String, Object> data = excelDataDto.getData();
            boolean exist = findDataByPrimaryKeys(excelDto, primaryKeys);
            try {
                if (exist) {
                    if (isCover) {
                        // 存在数据，设置为覆盖
                        updateDate(excelDto, data, primaryKeys);
                        applicationContext.publishEvent(new ExcelEvent(this, excelDto.getSqlName(), data, userNo, true));
                    } else {
                        // 存在数据，设置为不覆盖
                        isSuccess = false;
                        taskErrorDto.setErrorMessage("第" + (i + 1) + "条数据与数据库中的数据重复；");
                        taskErrorMapper.insertTaskError(taskErrorDto);
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
                    applicationContext.publishEvent(new ExcelEvent(this, excelDto.getSqlName(), data, userNo, false));
                }
            } catch (Exception exception) {
                isSuccess = false;
                taskErrorDto.setErrorMessage("第" + (i + 1) + "条数据" + (exist ? "更新" : "插入") + "出现异常；");
                taskErrorMapper.insertTaskError(taskErrorDto);
                logTask.logInfo(exception.getMessage());
            }
        }
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(taskCode);
        taskDto.setTaskStatus(isSuccess ? Constant.TASK_SUCCESS : Constant.TASK_ERROR);
        taskDto.setUpdatedBy(userNo);
        taskMapper.updateTask(taskDto);
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

    private void insertData(ExcelDto excelDto, List<String> keys, List<Object> values) {
        dataMapper.insertData(excelDto, keys, values);
    }

    private void updateDate(ExcelDto excelDto, Map<String, Object> data, Map<String, Object> primaryKeys) {
        dataMapper.updateData(excelDto, data, primaryKeys);
    }

    @Override
    @Async("uploadExcelExecutor")
    @Transactional(rollbackFor = RuntimeException.class)
    public void testAsync(String code) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(code);
        taskDto.setTaskStatus(Constant.TASK_SUCCESS);
        // 模拟异常请情况
        int i = 1 / 0;
        taskMapper.updateTask(taskDto);
    }

}
