package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class ExcelExistException extends BaseException {

    public ExcelExistException() {
        super("Excel映射配置不存在");
    }

}
