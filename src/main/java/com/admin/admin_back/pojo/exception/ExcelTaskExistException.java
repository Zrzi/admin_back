package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class ExcelTaskExistException extends BaseException {

    public ExcelTaskExistException() {
        super("Excel上传任务不存在");
    }

}
