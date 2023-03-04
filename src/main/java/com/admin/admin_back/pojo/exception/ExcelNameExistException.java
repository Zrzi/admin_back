package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class ExcelNameExistException extends BaseException {

    public ExcelNameExistException() {
        super("Excel名称已经存在");
    }

}
