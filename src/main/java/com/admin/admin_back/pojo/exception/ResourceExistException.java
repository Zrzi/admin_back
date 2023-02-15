package com.admin.admin_back.pojo.exception;

public class ResourceExistException extends BaseException {

    public ResourceExistException() {
        super("资源不存在");
    }

}
