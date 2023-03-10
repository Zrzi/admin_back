package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.*;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.*;
import com.admin.admin_back.pojo.enums.CodeTypeEnum;
import com.admin.admin_back.pojo.excel.ExcelAnalysisListener;
import com.admin.admin_back.pojo.exception.*;
import com.admin.admin_back.pojo.form.ExcelColumnForm;
import com.admin.admin_back.pojo.form.ExcelForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.ExcelColumnVo;
import com.admin.admin_back.pojo.vo.ExcelTaskVo;
import com.admin.admin_back.pojo.vo.ExcelVo;
import com.admin.admin_back.service.ExcelHelper;
import com.admin.admin_back.service.ExcelService;
import com.admin.admin_back.utils.GenerateCodeUtil;
import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 陈群矜
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ExcelMapper excelMapper;

    @Autowired
    private ExcelColumnMapper excelColumnMapper;

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskErrorMapper taskErrorMapper;

    @Autowired
    private ExcelHelper excelHelper;

    @Override
    public List<ExcelVo> getExcels() {
        return excelMapper
                .findExcels()
                .stream()
                .map(this::getExcelVoFromExcelDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExcelVo getExcelByExcelId(String excelId) {
        ExcelDto excelDto = excelMapper.findExcelByExcelId(excelId);
        if (Objects.isNull(excelDto)) {
            throw new ExcelExistException();
        }
        ExcelVo excelVo = getExcelVoFromExcelDto(excelDto);
        List<ExcelColumnDto> excelColumnDtos = excelColumnMapper.findExcelColumnsByExcelId(excelId);
        if (CollectionUtils.isEmpty(excelColumnDtos)) {
            return excelVo;
        }
        List<ExcelColumnVo> excelColumnVos = new ArrayList<>();
        for (ExcelColumnDto excelColumnDto : excelColumnDtos) {
            excelColumnVos.add(getExcelColumnVoFromExcelColumnDto(excelColumnDto));
        }
        excelVo.setRows(excelColumnVos);
        return excelVo;
    }

    @Override
    public List<String> getSqlTables() {
        return sqlMapper.findSqlTableNames(Constant.TABLE_SCHEMA);
    }

    @Override
    public List<String> getSqlColumns(String sqlTableName) {
        return sqlMapper.findSqlColumnNames(Constant.TABLE_SCHEMA, sqlTableName);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addExcel(ExcelForm excelForm) {
        ExcelDto excelDto = excelMapper.findExcelByExcelName(excelForm.getExcelName());
        if (Objects.nonNull(excelDto)) {
            throw new ExcelNameExistException();
        }
        excelDto = getExcelDtoFromExcelForm(excelForm, false);
        excelMapper.insertExcelDto(excelDto);
        saveExcelColumns(excelForm.getRows(), excelDto.getExcelId());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateExcel(ExcelForm excelForm) {
        String excelId = excelForm.getExcelId();
        ExcelDto excelDto = excelMapper.findExcelByExcelId(excelForm.getExcelId());
        if (Objects.isNull(excelDto)) {
            throw new ExcelExistException();
        }
        excelDto = getExcelDtoFromExcelForm(excelForm, true);
        excelDto.setExcelId(excelId);
        excelMapper.updateExcelDto(excelDto);
        excelColumnMapper.batchDeleteExcelColumns(excelId);
        saveExcelColumns(excelForm.getRows(), excelId);
    }

    private void saveExcelColumns(List<ExcelColumnForm> rows, String excelId) {
        if (!CollectionUtils.isEmpty(rows)) {
            List<ExcelColumnDto> excelColumnDtos = new ArrayList<>();
            for (ExcelColumnForm row : rows) {
                excelColumnDtos.add(getExcelColumnDtoFromExcelColumnForm(row, excelId));
            }
            excelColumnMapper.batchInsertExcelColumns(excelColumnDtos);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteExcel(String excelId) {
        ExcelDto excelDto = excelMapper.findExcelByExcelId(excelId);
        if (Objects.isNull(excelDto)) {
            throw new ExcelExistException();
        }
        String userNo = UserThreadLocal.getUser().getUserNo();
        excelDto.setUpdatedBy(userNo);
        excelMapper.deleteExcelDto(excelDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String uploadExcel(MultipartFile file) {
        ExcelAnalysisListener listener = new ExcelAnalysisListener(excelMapper, excelColumnMapper, sqlMapper);
        try {
            String userNo = UserThreadLocal.getUser().getUserNo();
            EasyExcel
                    .read(file.getInputStream(), listener)
                    .sheet()
                    .doRead();
            ExcelDto excelDto = listener.getExcelDto();
            String sqlName = excelDto.getSqlName();
            List<ExcelDataDto> dataList = listener.getDataList();
            boolean isCover = excelDto.getIsCover() == Constant.IS_COVER;
            String code = GenerateCodeUtil.generateCode(CodeTypeEnum.TASK);
            TaskDto taskDto = new TaskDto();
            taskDto.setTaskId(code);
            taskDto.setTaskStatus(Constant.TASK_CREATE);
            taskDto.setCreatedBy(userNo);
            taskDto.setUpdatedBy(userNo);
            taskMapper.insertTask(taskDto);
            if (StringUtils.equalsAny(sqlName, Constant.STUDENT_TABLE, Constant.TEACHER_TABLE)) {
                if (StringUtils.equals(sqlName, Constant.STUDENT_TABLE)) {
                    excelHelper.batchSaveStudent(code, excelDto, dataList, isCover, userNo);
                } else {
                    excelHelper.batchSaveTeacher(code, excelDto, dataList, isCover, userNo);
                }
            } else {
                excelHelper.batchSave(code, excelDto, dataList, isCover, userNo);
            }
            return code;
        } catch (IOException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public ExcelTaskVo getUploadExcelResult(String taskId) {
        TaskDto taskDto = taskMapper.findTaskByTaskId(taskId);
        if (Objects.isNull(taskDto)) {
            throw new ExcelTaskExistException();
        }
        ExcelTaskVo excelTaskVo = new ExcelTaskVo();
        switch (taskDto.getTaskStatus()) {
            case Constant.TASK_CREATE:
                excelTaskVo.setTaskStatus(Constant.TASK_CREATE);
                break;
            case Constant.TASK_SUCCESS:
                excelTaskVo.setTaskStatus(Constant.TASK_SUCCESS);
                break;
            case Constant.TASK_ERROR:
                excelTaskVo.setTaskStatus(Constant.TASK_ERROR);
                List<TaskErrorDto> taskErrors = taskErrorMapper.findTaskErrorsByTaskId(taskId);
                List<String> errors = taskErrors.stream().map(TaskErrorDto::getErrorMessage).collect(Collectors.toList());
                excelTaskVo.setErrorMessage(errors);
                break;
            default:
                throw new ExcelTaskExistException();
        }
        return excelTaskVo;
    }

    private ExcelVo getExcelVoFromExcelDto(ExcelDto excelDto) {
        ExcelVo excelVo = new ExcelVo();
        excelVo.setExcelId(excelDto.getExcelId());
        excelVo.setExcelName(excelDto.getExcelName());
        excelVo.setSqlName(excelDto.getSqlName());
        excelVo.setIsCover(excelDto.getIsCover() == 1);
        excelVo.setRows(null);
        return excelVo;
    }

    private ExcelColumnVo getExcelColumnVoFromExcelColumnDto(ExcelColumnDto excelColumnDto) {
        ExcelColumnVo excelColumnVo = new ExcelColumnVo();
        excelColumnVo.setExcelId(excelColumnDto.getExcelId());
        excelColumnVo.setExcelColumn(excelColumnDto.getExcelColumn());
        excelColumnVo.setSqlColumn(excelColumnDto.getSqlColumn());
        excelColumnVo.setIsPrimaryKey(excelColumnDto.getIsPrimaryKey() == 1);
        return excelColumnVo;
    }

    private ExcelDto getExcelDtoFromExcelForm(ExcelForm excelForm, boolean isUpdate) {
        String userNo = UserThreadLocal.getUser().getUserNo();
        ExcelDto excelDto = new ExcelDto();
        excelDto.setExcelName(excelForm.getExcelName());
        excelDto.setSqlName(excelForm.getSqlName());
        excelDto.setIsCover(excelForm.getIsCover() ? 1 : 0);
        if (!isUpdate) {
            excelDto.setExcelId(GenerateCodeUtil.generateCode(CodeTypeEnum.EXCEL));
            excelDto.setCreatedBy(userNo);
        }
        excelDto.setUpdatedBy(userNo);
        return excelDto;
    }

    private ExcelColumnDto getExcelColumnDtoFromExcelColumnForm(ExcelColumnForm excelColumnForm, String excelId) {
        String userNo = UserThreadLocal.getUser().getUserNo();
        ExcelColumnDto excelColumnDto = new ExcelColumnDto();
        excelColumnDto.setExcelId(excelId);
        excelColumnDto.setExcelColumn(excelColumnForm.getExcelColumn());
        excelColumnDto.setSqlColumn(excelColumnForm.getSqlColumn());
        excelColumnDto.setIsPrimaryKey(excelColumnForm.getIsPrimaryKey() ? 1 : 0);
        excelColumnDto.setCreatedBy(userNo);
        excelColumnDto.setUpdatedBy(userNo);
        return excelColumnDto;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void testAsync() {
        String code = GenerateCodeUtil.generateCode(CodeTypeEnum.TASK);
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(code);
        taskDto.setTaskStatus(Constant.TASK_CREATE);
        taskMapper.insertTask(taskDto);
        excelHelper.testAsync(code);
    }

}
