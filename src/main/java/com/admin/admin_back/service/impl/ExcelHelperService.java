package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.DataMapper;
import com.admin.admin_back.mapper.TaskErrorMapper;
import com.admin.admin_back.mapper.TaskMapper;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.ExcelDataDto;
import com.admin.admin_back.pojo.dto.ExcelDto;
import com.admin.admin_back.pojo.dto.TaskDto;
import com.admin.admin_back.pojo.dto.TaskErrorDto;
import com.admin.admin_back.service.ExcelHelper;
import com.admin.admin_back.service.LogTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    @Override
    @Async("uploadExcelExecutor")
    @Transactional(rollbackFor = RuntimeException.class)
    public void batchSave(String taskCode, ExcelDto excelDto, List<ExcelDataDto> dataList, boolean isCover) {
        TaskErrorDto taskErrorDto = new TaskErrorDto();
        taskErrorDto.setTaskId(taskCode);
        boolean isSuccess = true;
        int size = dataList.size();
        for (int i=0; i<size; ++i) {
            ExcelDataDto excelDataDto = dataList.get(i);
            Map<String, String> primaryKeys = excelDataDto.getPrimaryKeys();
            Map<String, String> data = excelDataDto.getData();
            boolean exist = findDataByPrimaryKeys(excelDto, primaryKeys);
            try {
                if (exist) {
                    if (isCover) {
                        // 存在数据，设置为覆盖
                        updateDate(excelDto, data, primaryKeys);
                    } else {
                        // 存在数据，设置为不覆盖
                        isSuccess = false;
                        taskErrorDto.setErrorMessage("第" + (i + 1) + "条数据与数据库中的数据重复；");
                        taskErrorMapper.insertTaskError(taskErrorDto);
                    }
                } else {
                    // 不存在数据
                    insertData(excelDto, data);
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
        taskMapper.updateTask(taskDto);
    }

    /**
     * 根据主键查询数据库中是否存在相关数据
     * @param excelDto 用于传入sqlName
     * @param primaryKeys 主键及对应值
     * @return true代表存在；false代表不存在
     */
    private boolean findDataByPrimaryKeys(ExcelDto excelDto, Map<String, String> primaryKeys) {
        return dataMapper.selectCountDataByPrimaryKeys(excelDto, primaryKeys) != 0;
    }

    private void insertData(ExcelDto excelDto, Map<String, String> data) {
        dataMapper.insertData(excelDto, data);
    }

    private void updateDate(ExcelDto excelDto, Map<String, String> data, Map<String, String> primaryKeys) {
        dataMapper.updateData(excelDto, data, primaryKeys);
    }

}
