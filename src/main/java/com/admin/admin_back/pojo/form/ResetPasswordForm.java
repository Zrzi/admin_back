package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("重置密码参数")
public class ResetPasswordForm {

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

    public ResetPasswordForm() {}

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
