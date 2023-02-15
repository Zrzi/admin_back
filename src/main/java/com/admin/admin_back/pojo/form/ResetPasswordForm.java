package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class ResetPasswordForm {

    private String userNo;

    private String oldPassword;

    private String newPassword;

    public ResetPasswordForm() {}

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
