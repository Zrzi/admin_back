package com.admin.admin_back.pojo.exception;

public class ResourceTypeException extends BaseException {

    public ResourceTypeException() {
        super("资源类型与父资源类型不一致");
    }

}
