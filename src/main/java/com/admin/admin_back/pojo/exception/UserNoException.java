package com.admin.admin_back.pojo.exception;

/**
 * @author 陈群矜
 */
public class UserNoException extends BaseException {

    public UserNoException() {
        super("用户名已经存在");
    }

}
