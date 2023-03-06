package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.annotations.LogAnnotation;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.exception.ExcelDataException;
import com.admin.admin_back.pojo.exception.ExcelExistException;
import com.admin.admin_back.pojo.exception.ExcelNameExistException;
import com.admin.admin_back.pojo.exception.SqlColumnNameNotFoundException;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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

    @ApiOperation("获取数据库中的所有sql表格名称")
    @LogAnnotation
    @CheckRole("getSqlTables")
    @GetMapping("/excel/getSqlTables")
    public Result<?> getSqlTables() {
        return new Result<>(ResponseMessage.SUCCESS, excelService.getSqlTables());
    }

    @ApiOperation("根据数据库表格名称获取数据库中表格的所有列名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sqlTableName", value = "数据库表格名称", required = true)
    })
    @LogAnnotation
    @CheckRole("getSqlColumns")
    @GetMapping("/excel/getSqlColumns")
    public Result<?> getSqlColumns(@RequestParam("sqlTableName") String sqlTableName) {
        return new Result<>(ResponseMessage.SUCCESS, excelService.getSqlColumns(sqlTableName));
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
        try {
            excelService.addExcel(excelForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (ExcelNameExistException exception) {
            return new Result<>(ResponseMessage.EXCEL_NAME_EXIST);
        }
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
        try {
            excelService.updateExcel(excelForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (ExcelExistException exception) {
            return new Result<>(ResponseMessage.EXCEL_NOT_FOUND);
        }
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

    @ApiOperation("上传Excel文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "Excel文件", required = true)
    })
    @LogAnnotation(inEnabled = false)
    @CheckRole("uploadExcel")
    @PostMapping("/excel/upload")
    public Result<?> uploadExcel(@RequestPart("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (Objects.isNull(filename)) {
            return new Result<>(ResponseMessage.FILE_TYPE_ERROR);
        }
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        if (!StringUtils.equalsAny(suffix, Constant.FILE_XLS, Constant.FILE_XLSX)) {
            return new Result<>(ResponseMessage.FILE_TYPE_ERROR);
        }
        try {
            excelService.uploadExcel(file);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (ExcelNameExistException exception) {
            return new Result<>(ResponseMessage.EXCEL_NAME_NOT_FOUND);
        } catch (SqlColumnNameNotFoundException exception) {
            return new Result<>(ResponseMessage.EXCEL_COLUMN_NAME_ERROR, null, exception.getMessage());
        } catch (ExcelDataException exception) {
            return new Result<>(ResponseMessage.EXCEL_DATA_ERROR, null, exception.getMessage());
        } catch (Exception exception) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
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
        Boolean isCover = excelForm.getIsCover();
        if (isCover == null) {
            excelForm.setIsCover(false);
        }
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
                return "第" + (i + 1) + "项Excel列名为空";
            }
            excelColumn = excelColumn.trim();
            if (excelColumn.length() > 16) {
                return "第" + (i + 1) + "项Excel列名长度大于16位";
            }
            if (excelColumnSet.contains(excelColumn)) {
                return "第" + (i + 1) + "项Excel列名重复";
            }
            excelColumnSet.add(excelColumn);
            excelColumnForm.setExcelColumn(excelColumn);
            String sqlColumn = excelColumnForm.getSqlColumn();
            if (StringUtils.isBlank(sqlColumn)) {
                return "第" + (i + 1) + "项sql列名为空";
            }
            sqlColumn = sqlColumn.trim();
            if (sqlColumn.length() > 40) {
                return "第" + (i + 1) + "项sql列名长度大于40位";
            }
            if (sqlColumnSet.contains(sqlColumn)) {
                return "第" + (i + 1) + "项sql列名重复";
            }
            sqlColumnSet.add(sqlColumn);
            excelColumnForm.setSqlColumn(sqlColumn);
        }
        return "";
    }

}
