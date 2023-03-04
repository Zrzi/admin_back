package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.ExcelColumnMapper;
import com.admin.admin_back.mapper.ExcelMapper;
import com.admin.admin_back.mapper.SqlMapper;
import com.admin.admin_back.pojo.dto.ExcelColumnDto;
import com.admin.admin_back.pojo.dto.ExcelDto;
import com.admin.admin_back.pojo.enums.CodeTypeEnum;
import com.admin.admin_back.pojo.exception.ExcelExistException;
import com.admin.admin_back.pojo.exception.ExcelNameExistException;
import com.admin.admin_back.pojo.form.ExcelColumnForm;
import com.admin.admin_back.pojo.form.ExcelForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.ExcelColumnVo;
import com.admin.admin_back.pojo.vo.ExcelVo;
import com.admin.admin_back.service.ExcelService;
import com.admin.admin_back.utils.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    private final static String SQL_DATABASE_NAME = "emp";

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
        return sqlMapper.findSqlTableNames(SQL_DATABASE_NAME);
    }

    @Override
    public List<String> getSqlColumns(String sqlTableName) {
        return sqlMapper.findSqlColumnNames(SQL_DATABASE_NAME, sqlTableName);
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
        ExcelDto excelDto = excelMapper.findExcelByExcelId(excelForm.getExcelId());
        if (Objects.isNull(excelDto)) {
            throw new ExcelExistException();
        }
        excelDto = getExcelDtoFromExcelForm(excelForm, true);
        excelMapper.updateExcelDto(excelDto);
        String excelId = excelDto.getExcelId();
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

    private ExcelVo getExcelVoFromExcelDto(ExcelDto excelDto) {
        ExcelVo excelVo = new ExcelVo();
        excelVo.setExcelId(excelDto.getExcelId());
        excelVo.setExcelName(excelDto.getExcelName());
        excelVo.setSqlName(excelDto.getSqlName());
        excelVo.setRows(null);
        return excelVo;
    }

    private ExcelColumnVo getExcelColumnVoFromExcelColumnDto(ExcelColumnDto excelColumnDto) {
        ExcelColumnVo excelColumnVo = new ExcelColumnVo();
        excelColumnVo.setExcelId(excelColumnDto.getExcelId());
        excelColumnVo.setExcelColumn(excelColumnDto.getExcelColumn());
        excelColumnVo.setSqlColumn(excelColumnDto.getSqlColumn());
        return excelColumnVo;
    }

    private ExcelDto getExcelDtoFromExcelForm(ExcelForm excelForm, boolean isUpdate) {
        String userNo = UserThreadLocal.getUser().getUserNo();
        ExcelDto excelDto = new ExcelDto();
        excelDto.setExcelName(excelForm.getExcelName());
        excelDto.setSqlName(excelForm.getSqlName());
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

}
