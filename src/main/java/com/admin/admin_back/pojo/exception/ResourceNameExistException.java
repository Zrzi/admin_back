package com.admin.admin_back.pojo.exception;

public class ResourceNameExistException extends BaseException {

    public ResourceNameExistException() {
        super("资源名称已经存在");
    }

}
