package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class LoginForm {

    private String userNo;

    private String password;

    public LoginForm() {}

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
