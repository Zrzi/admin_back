package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.annotations.LogAnnotation;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.ExcelExistException;
import com.admin.admin_back.pojo.form.DeleteExcelForm;
import com.admin.admin_back.pojo.form.ExcelColumnForm;
import com.admin.admin_back.pojo.form.ExcelForm;
import com.admin.admin_back.pojo.vo.ExcelVo;
import com.admin.admin_back.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 陈群矜
 */
@Api(tags = "excel映射配置相关接口")
@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation("获取所有Excel映射配置")
    @LogAnnotation
    @CheckRole("getExcels")
    @GetMapping("/excel/getExcels")
    public Result<?> getExcels() {
        return new Result<>(ResponseMessage.SUCCESS, excelService.getExcels());
    }

    @ApiOperation("根据编码获取Excel映射配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "excelId", value = "Excel映射配置编码", required = true)
    })
    @LogAnnotation
    @CheckRole("getExcelByExcelId")
    @GetMapping("/excel/getExcelByExcelId")
    public Result<?> getExcelByExcelId(@RequestParam("excelId") String excelId) {
        if (StringUtils.isBlank(excelId)) {
            return new Result<>(ResponseMessage.EXCEL_ID_IS_NULL);
        }
        try {
            ExcelVo excelVo = excelService.getExcelByExcelId(excelId);
            return new Result<>(ResponseMessage.SUCCESS, excelVo);
        } catch (ExcelExistException exception) {
            return new Result<>(ResponseMessage.EXCEL_NOT_FOUND);
        }
    }

    @ApiOperation("添加Excel映射配置")
    @LogAnnotation
    @CheckRole("addExcel")
    @PostMapping("/excel/add")
    public Result<?> addExcel(@RequestBody ExcelForm excelForm) {
        String flag = checkExcelForm(excelForm, false);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.EXCEL_FORM_ERROR, null, flag);
        }
        return null;
    }

    @ApiOperation("编辑Excel映射配置")
    @LogAnnotation
    @CheckRole("updateExcel")
    @PostMapping("/excel/update")
    public Result<?> updateExcel(@RequestBody ExcelForm excelForm) {
        String flag = checkExcelForm(excelForm, true);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.EXCEL_FORM_ERROR, null, flag);
        }
        return null;
    }

    @ApiOperation("删除Excel映射配置")
    @LogAnnotation
    @CheckRole("deleteExcel")
    @PostMapping("/excel/delete")
    public Result<?> deleteExcel(@RequestBody DeleteExcelForm deleteExcelForm) {
        String excelId = deleteExcelForm.getExcelId();
        if (StringUtils.isBlank(excelId)) {
            return new Result<>(ResponseMessage.EXCEL_ID_IS_NULL);
        }
        try {
            excelService.deleteExcel(excelId);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (ExcelExistException exception) {
            return new Result<>(ResponseMessage.EXCEL_NOT_FOUND);
        }
    }

    private String checkExcelForm(ExcelForm excelForm, boolean isUpdate) {
        if (isUpdate) {
            if (StringUtils.isBlank(excelForm.getExcelId())) {
                return "Excel配置编码不存在";
            }
        }
        String excelName = excelForm.getExcelName();
        if (StringUtils.isBlank(excelName)) {
            return "Excel表格名称不存在";
        }
        excelName = excelName.trim();
        if (excelName.length() > 16) {
            return "Excel表格名称最长16个字符";
        }
        excelForm.setExcelName(excelName);
        String sqlName = excelForm.getSqlName();
        if (StringUtils.isBlank(sqlName)) {
            return "sql名称不存在";
        }
        sqlName = sqlName.trim();
        if (sqlName.length() > 40) {
            return "sql名称最长40个字符";
        }
        excelForm.setSqlName(sqlName);
        return checkExcelColumns(excelForm.getRows());
    }

    private String checkExcelColumns(List<ExcelColumnForm> excelColumnForms) {
        if (CollectionUtils.isEmpty(excelColumnForms)) {
            return "列映射配置至少有一列";
        }
        int size = excelColumnForms.size();
        Set<String> excelColumnSet = new HashSet<>();
        Set<String> sqlColumnSet = new HashSet<>();
        for (int i=0; i<size; ++i) {
            ExcelColumnForm excelColumnForm = excelColumnForms.get(i);
            String excelColumn = excelColumnForm.getExcelColumn();
            if (StringUtils.isBlank(excelColumn)) {
                return "第" + i + "项Excel列名为空";
            }
            excelColumn = excelColumn.trim();
            if (excelColumn.length() > 16) {
                return "第" + i + "项Excel列名长度大于16位";
            }
            if (excelColumnSet.contains(excelColumn)) {
                return "第" + i + "项Excel列名重复";
            }
            excelColumnSet.add(excelColumn);
            excelColumnForm.setExcelColumn(excelColumn);
            String sqlColumn = excelColumnForm.getSqlColumn();
            if (StringUtils.isBlank(sqlColumn)) {
                return "第" + i + "项sql列名为空";
            }
            sqlColumn = sqlColumn.trim();
            if (sqlColumn.length() > 40) {
                return "第" + i + "项sql列名长度大于40位";
            }
            if (sqlColumnSet.contains(sqlColumn)) {
                return "第" + i + "项sql列名重复";
            }
            sqlColumnSet.add(sqlColumn);
            excelColumnForm.setSqlColumn(sqlColumn);
        }
        return "";
    }

}
