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
import com.admin.admin_back.pojo.vo.*;
import com.admin.admin_back.service.DeleteCacheService;
import com.admin.admin_back.service.ExcelHelper;
import com.admin.admin_back.service.ExcelService;
import com.admin.admin_back.service.LogTask;
import com.admin.admin_back.utils.CacheTimeUtil;
import com.admin.admin_back.utils.GenerateCodeUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private LogTask logTask;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CacheTimeUtil cacheTimeUtil;

    @Autowired
    private DeleteCacheService deleteCacheService;

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
        String cacheValue = stringRedisTemplate.opsForValue().get("excel:" + excelId);
        if (StringUtils.isNotBlank(cacheValue)) {
            return JSON.parseObject(cacheValue, ExcelVo.class);
        }
        ExcelDto excelDto = excelMapper.findExcelByExcelId(excelId);
        if (Objects.isNull(excelDto)) {
            throw new ExcelExistException();
        }
        ExcelVo excelVo = getExcelVoFromExcelDto(excelDto);
        List<ExcelColumnDto> excelColumnDtos = excelColumnMapper.findExcelColumnsByExcelId(excelId);
        if (CollectionUtils.isEmpty(excelColumnDtos)) {
            return excelVo;
        }
        String sqlTableName = excelVo.getSqlName();
        Set<String> nonNullSet = new HashSet<>();
        Set<String> nullableSet = new HashSet<>();
        classifyColumnNames(sqlTableName, nonNullSet, nullableSet);
        List<ExcelColumnVo> nonNullColumns = excelVo.getNonNullList();
        List<ExcelColumnVo> nullableColumns = excelVo.getNullableList();
        for (ExcelColumnDto excelColumnDto : excelColumnDtos) {
            if (excelColumnDto.getIsSpecial() == Constant.IS_SPECIAL) {
                nonNullColumns.add(getExcelColumnVoFromExcelColumnDto(excelColumnDto));
                continue;
            }
            String sqlColumn = excelColumnDto.getSqlColumn();
            if (nonNullSet.contains(sqlColumn)) {
                nonNullColumns.add(getExcelColumnVoFromExcelColumnDto(excelColumnDto));
            } else if (nullableSet.contains(sqlColumn)) {
                nullableColumns.add(getExcelColumnVoFromExcelColumnDto(excelColumnDto));
            }
        }
        stringRedisTemplate.opsForValue().set("excel:" + excelId, excelVo.toString(), cacheTimeUtil.getCacheTime(), TimeUnit.SECONDS);
        return excelVo;
    }

    @Override
    public List<String> getSqlTables() {
        return sqlMapper.findSqlTableNames(Constant.TABLE_SCHEMA);
    }

    @Override
    public GetSqlColumnsVo getSqlColumns(String sqlTableName) {
        // 获取主键名称集合
        GetSqlColumnsVo result = new GetSqlColumnsVo();
        List<String> nonNullList = result.getNonNullList();
        List<String> nullableList = result.getNullableList();
        classifyColumnNames(sqlTableName, nonNullList, nullableList);
        return result;
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
        String key = "excel:" + excelId;
        deleteCacheService.deleteRedisCache(key);
        deleteCacheService.deleteRedisCache(key, Constant.INT_2);
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
        String key = "excel:" + excelId;
        deleteCacheService.deleteRedisCache(key);
        deleteCacheService.deleteRedisCache(key, Constant.INT_2);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String uploadExcel(MultipartFile file) {
        String userNo = UserThreadLocal.getUser().getUserNo();
        ExcelAnalysisListener listener = new ExcelAnalysisListener(excelMapper, excelColumnMapper, sqlMapper, logTask);
        try {
            EasyExcel
                    .read(file.getInputStream(), listener)
                    .sheet()
                    .doRead();
            ExcelDto excelDto = listener.getExcelDto();
            List<ExcelDataDto> dataList = listener.getDataList();
            List<List<String>> uniqueKeys = listener.getUniqueKeys();
            boolean isCover = excelDto.getIsCover() == Constant.IS_COVER;
            String code = GenerateCodeUtil.generateCode(CodeTypeEnum.TASK);
            TaskDto taskDto = new TaskDto();
            taskDto.setExcelName(excelDto.getExcelName());
            taskDto.setSqlName(excelDto.getSqlName());
            taskDto.setTaskId(code);
            taskDto.setTaskStatus(Constant.TASK_CREATE);
            taskDto.setCreatedBy(userNo);
            taskDto.setUpdatedBy(userNo);
            taskMapper.insertTask(taskDto);
            excelHelper.batchSave(code, excelDto, dataList, uniqueKeys, isCover, userNo);
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
        return getExcelTaskVoFromTaskDto(taskDto, taskId);
    }

    @Override
    public GetHistoryUploadExcelResult getHistoryUploadExcelResult(int start, int pageSize) {
        GetHistoryUploadExcelResult result = new GetHistoryUploadExcelResult();
        List<ExcelTaskVo> excelTasks = result.getExcelTasks();
        String userNo = UserThreadLocal.getUser().getUserNo();
        List<TaskDto> taskDtos = taskMapper.findTaskByUserNo(userNo, start, pageSize);
        result.setTotal(taskMapper.findTotalCountByUserNo(userNo));
        if (CollectionUtils.isEmpty(taskDtos)) {
            return result;
        }
        for (TaskDto taskDto : taskDtos) {
            excelTasks.add(getExcelTaskVoFromTaskDto(taskDto, taskDto.getTaskId()));
        }
        return result;
    }

    /**
     * 查询是否已经存在Excel配置
     * @param excelName Excel表格名称
     * @return true：不存在；false：存在
     */
    @Override
    public boolean checkIfExistExcelName(String excelName) {
        return Objects.isNull(excelMapper.findExcelByExcelName(excelName));
    }

    private ExcelVo getExcelVoFromExcelDto(ExcelDto excelDto) {
        ExcelVo excelVo = new ExcelVo();
        excelVo.setExcelId(excelDto.getExcelId());
        excelVo.setExcelName(excelDto.getExcelName());
        excelVo.setSqlName(excelDto.getSqlName());
        excelVo.setIsCover(excelDto.getIsCover() == Constant.IS_COVER);
        return excelVo;
    }

    private ExcelColumnVo getExcelColumnVoFromExcelColumnDto(ExcelColumnDto excelColumnDto) {
        ExcelColumnVo excelColumnVo = new ExcelColumnVo();
        excelColumnVo.setExcelId(excelColumnDto.getExcelId());
        excelColumnVo.setExcelColumn(excelColumnDto.getExcelColumn());
        excelColumnVo.setSqlColumn(excelColumnDto.getSqlColumn());
        excelColumnVo.setIsSpecial(excelColumnDto.getIsSpecial() == Constant.IS_SPECIAL);
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
        excelColumnDto.setIsSpecial(excelColumnForm.getIsSpecial() ? 1 : 0);
        excelColumnDto.setCreatedBy(userNo);
        excelColumnDto.setUpdatedBy(userNo);
        return excelColumnDto;
    }

//    @Override
//    @Transactional(rollbackFor = RuntimeException.class)
//    public void testAsync() {
//        String code = GenerateCodeUtil.generateCode(CodeTypeEnum.TASK);
//        TaskDto taskDto = new TaskDto();
//        taskDto.setTaskId(code);
//        taskDto.setTaskStatus(Constant.TASK_CREATE);
//        taskMapper.insertTask(taskDto);
//        excelHelper.testAsync(code);
//    }

    private void classifyColumnNames(String sqlTableName,
                                     Collection<String> nonNullCollection,
                                     Collection<String> nullableCollection) {
        Set<String> primaryKeyNames = sqlMapper
                .findSqlConstraintByType(Constant.TABLE_SCHEMA, sqlTableName, Constant.CONSTRAINT_TYPE_PRIMARY_KEY)
                .stream()
                .map(SqlConstraintDto::getColumnName)
                .collect(Collectors.toSet());
        List<SqlColumnInfoDto> sqlColumnInfos = sqlMapper.findSqlColumnInfos(Constant.TABLE_SCHEMA, sqlTableName);
        for (SqlColumnInfoDto sqlColumnInfo : sqlColumnInfos) {
            if (StringUtils.equals(sqlColumnInfo.getExtra(), Constant.AUTO_INCREMENT)) {
                // 不需要用户填写，直接跳过
                continue;
            }
            if (!StringUtils.equals(sqlColumnInfo.getIsNullable(), Constant.IS_NULLABLE)) {
                // NOT NULL 字段，必须添加
                nonNullCollection.add(sqlColumnInfo.getColumnName());
                continue;
            }
            if (primaryKeyNames.contains(sqlColumnInfo.getColumnName())) {
                // 是主键
                nonNullCollection.add(sqlColumnInfo.getColumnName());
                continue;
            }
            nullableCollection.add(sqlColumnInfo.getColumnName());
        }
    }

    private ExcelTaskVo getExcelTaskVoFromTaskDto(TaskDto taskDto, String taskId) {
        ExcelTaskVo excelTaskVo = new ExcelTaskVo();
        excelTaskVo.setTaskId(taskId);
        excelTaskVo.setExcelName(taskDto.getExcelName());
        excelTaskVo.setSqlName(taskDto.getSqlName());
        excelTaskVo.setTaskSuccessInsert(taskDto.getTaskSuccessInsert());
        excelTaskVo.setTaskSuccessUpdate(taskDto.getTaskSuccessUpdate());
        switch (taskDto.getTaskStatus()) {
            case Constant.TASK_CREATE:
                excelTaskVo.setTaskStatus(Constant.TASK_CREATE);
                break;
            case Constant.TASK_SUCCESS:
                excelTaskVo.setTaskStatus(Constant.TASK_SUCCESS);
                excelTaskVo.setCompletedDate(dateFormat(taskDto.getUpdatedDate()));
                break;
            case Constant.TASK_ERROR:
                excelTaskVo.setTaskStatus(Constant.TASK_ERROR);
                excelTaskVo.setCompletedDate(dateFormat(taskDto.getUpdatedDate()));
                List<TaskErrorDto> taskErrors = taskErrorMapper.findTaskErrorsByTaskId(taskId);
                List<String> errors = taskErrors.stream().map(TaskErrorDto::getErrorMessage).collect(Collectors.toList());
                excelTaskVo.setErrorMessage(errors);
                break;
            default:
                throw new ExcelTaskExistException();
        }
        return excelTaskVo;
    }

    private String dateFormat(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

}
