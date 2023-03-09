package com.admin.admin_back.pojo.dto;

import com.alibaba.fastjson.JSON;

/**
 * 记录sql列的详细信息
 * 包括列名、是否为空、数据类型
 * @author 陈群矜
 */
public class SqlColumnInfoDto {

    private String columnName;

    private String isNullable;

    private String dataType;

    public SqlColumnInfoDto() {}

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
