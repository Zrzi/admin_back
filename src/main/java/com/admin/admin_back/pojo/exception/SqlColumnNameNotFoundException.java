package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class SqlColumnNameNotFoundException extends BaseException {

    public SqlColumnNameNotFoundException() {
        super("不存在对应的字段");
    }

    public SqlColumnNameNotFoundException(String excelColumnName) {
        super(excelColumnName + "不存在对应的字段");
    }

}
