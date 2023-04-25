package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.Limit;
import com.admin.admin_back.annotations.LogAnnotation;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.form.GetResultForm;
import com.admin.admin_back.service.DataService;
import com.admin.admin_back.service.LogTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 陈群矜
 */
@Api(tags = "数据获取相关接口")
@RestController
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private LogTask logTask;

    @ApiOperation("根据数据库表名、列名以及查询条件获取数据")
    @Limit(1000)
    @LogAnnotation
    @ResponseBody
    @PostMapping("/getResults")
    public Result<?> getResults(@RequestBody GetResultForm getResultForm) {
        String flag = checkForm(getResultForm);
        if (StringUtils.isNotBlank(flag)) {
            logTask.logInfo("查询信息：" + getResultForm + "，错误：" + flag);
            return new Result<>(ResponseMessage.SYSTEM_ERROR, null, flag);
        }
        try {
            return new Result<>(ResponseMessage.SUCCESS, dataService.getResults(getResultForm));
        } catch (RuntimeException exception) {
            logTask.logInfo("查询信息：" + getResultForm + "，异常信息：" + exception.getMessage());
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

    private String checkForm(GetResultForm getResultForm) {
        // 检查数据库表名
        String sqlTableName = getResultForm.getSqlTableName();
        if (StringUtils.containsWhitespace(sqlTableName)) {
            return "表名不能含有空格";
        }
        if (!Constant.ALLOWED_SQL_TABLE_NAMES.contains(sqlTableName)) {
            return  sqlTableName + "不存在";
        }
        // 检查列名
        final List<String> names = Constant.PRIVACY_COLUMN_NAMES;
        final List<String> columnNames = getResultForm.getSqlColumnNames();
        if (CollectionUtils.isEmpty(columnNames)) {
            return "查询的列名不能为空";
        }
        for (String columnName : getResultForm.getSqlColumnNames()) {
            if (StringUtils.containsWhitespace(columnName)) {
                return "列名不能含有空格";
            }
            for (String name : names) {
                if (StringUtils.containsIgnoreCase(name, columnName)) {
                    return "可能存在敏感信息";
                }
            }
        }
        final Map<String, String> conditions = getResultForm.getConditions();
        if (!conditions.isEmpty()) {
            for (String key : conditions.keySet()) {
                if (StringUtils.containsWhitespace(key)) {
                    return "查询条件左值不能由空格";
                }
            }
        }
        return "";
    }

}
