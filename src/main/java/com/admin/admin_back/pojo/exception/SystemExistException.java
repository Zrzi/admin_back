package com.admin.admin_back.pojo.exception;

public class SystemExistException extends BaseException {

    public SystemExistException() {
        super("系统不存在");
    }

}
