package com.admin.admin_back.pojo.exception;

public class ResourceParentExistException extends BaseException {

    public ResourceParentExistException() {
        super("父资源不存在");
    }

}
