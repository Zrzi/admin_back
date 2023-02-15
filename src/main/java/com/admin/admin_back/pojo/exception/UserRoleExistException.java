package com.admin.admin_back.pojo.exception;

public class UserRoleExistException extends BaseException {

    public UserRoleExistException() {
        super("用户角色对应关系已经存在");
    }

}
