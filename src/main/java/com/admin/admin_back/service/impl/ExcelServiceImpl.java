package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.ExcelColumnMapper;
import com.admin.admin_back.mapper.ExcelMapper;
import com.admin.admin_back.pojo.dto.ExcelColumnDto;
import com.admin.admin_back.pojo.dto.ExcelDto;
import com.admin.admin_back.pojo.exception.ExcelExistException;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.ExcelColumnVo;
import com.admin.admin_back.pojo.vo.ExcelVo;
import com.admin.admin_back.service.ExcelService;
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

    public ExcelVo getExcelVoFromExcelDto(ExcelDto excelDto) {
        ExcelVo excelVo = new ExcelVo();
        excelVo.setExcelId(excelDto.getExcelId());
        excelVo.setExcelName(excelDto.getExcelName());
        excelVo.setSqlName(excelDto.getSqlName());
        excelVo.setRows(null);
        return excelVo;
    }

    public ExcelColumnVo getExcelColumnVoFromExcelColumnDto(ExcelColumnDto excelColumnDto) {
        ExcelColumnVo excelColumnVo = new ExcelColumnVo();
        excelColumnVo.setExcelId(excelColumnDto.getExcelId());
        excelColumnVo.setExcelColumn(excelColumnDto.getExcelColumn());
        excelColumnVo.setSqlColumn(excelColumnDto.getSqlColumn());
        return excelColumnVo;
    }

}
