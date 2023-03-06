package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class BaseException extends RuntimeException {

    private String message;

    public BaseException() {}

    public BaseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
