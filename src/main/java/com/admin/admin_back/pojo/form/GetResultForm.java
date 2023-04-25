package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author 陈群矜
 */
@ApiModel("数据获取接口请求信息")
public class GetResultForm {

    @ApiModelProperty(value = "数据库表格名称", required = true)
    private String sqlTableName;

    @ApiModelProperty(value = "列名的集合", required = true)
    private List<String> sqlColumnNames;

    @ApiModelProperty(value = "查询条件", required = true)
    private Map<String, String> conditions;

    public GetResultForm() {}

    public String getSqlTableName() {
        return sqlTableName;
    }

    public void setSqlTableName(String sqlTableName) {
        this.sqlTableName = sqlTableName;
    }

    public List<String> getSqlColumnNames() {
        return sqlColumnNames;
    }

    public void setSqlColumnNames(List<String> sqlColumnNames) {
        this.sqlColumnNames = sqlColumnNames;
    }

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
