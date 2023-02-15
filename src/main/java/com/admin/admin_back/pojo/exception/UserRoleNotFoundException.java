package com.admin.admin_back.pojo.exception;

public class UserRoleNotFoundException extends BaseException {

    public UserRoleNotFoundException() {
        super("用户角色对应关系不存在");
    }

}
