package com.admin.admin_back.pojo.exception;

public class RoleExistException extends BaseException {

    public RoleExistException() {
        super("角色不存在");
    }

}
