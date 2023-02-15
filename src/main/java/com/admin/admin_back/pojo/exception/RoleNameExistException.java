package com.admin.admin_back.pojo.exception;

public class RoleNameExistException extends BaseException {

    public RoleNameExistException() {
        super("角色名称已经存在");
    }

}
