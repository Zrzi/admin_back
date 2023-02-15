package com.admin.admin_back.pojo.exception;

public class SystemNameExistException extends BaseException {

    public SystemNameExistException() {
        super("系统名称已经存在");
    }

}
