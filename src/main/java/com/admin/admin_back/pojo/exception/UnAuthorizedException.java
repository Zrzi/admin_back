package com.admin.admin_back.pojo.exception;

public class UnAuthorizedException extends BaseException {

    public UnAuthorizedException() {
        super("未登录");
    }

}
