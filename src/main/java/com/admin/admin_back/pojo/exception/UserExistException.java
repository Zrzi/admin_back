package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class UserExistException extends BaseException {

    public UserExistException() {
        super("用户不存在");
    }

}
