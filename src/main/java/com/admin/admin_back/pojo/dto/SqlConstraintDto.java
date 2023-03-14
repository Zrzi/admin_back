package com.admin.admin_back.pojo.dto;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class SqlConstraintDto {

    private String constraintName;

    private String columnName;

    public SqlConstraintDto() {}

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
