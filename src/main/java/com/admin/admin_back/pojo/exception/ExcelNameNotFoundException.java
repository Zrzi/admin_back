package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class ExcelNameNotFoundException extends BaseException {

    public ExcelNameNotFoundException() {
        super("Excel映射名称不存在");
    }

}
