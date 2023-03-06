package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class ExcelDataException extends BaseException {

    public ExcelDataException() {
        super("Excel表格中存在数据错误");
    }

    public ExcelDataException(String message) {
        super(message);
    }

}
