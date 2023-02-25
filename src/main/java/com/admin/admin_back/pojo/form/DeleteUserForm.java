package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("删除用户接口参数信息")
public class DeleteUserForm {

    @ApiModelProperty("用户名")
    private String userNo;

    public DeleteUserForm() {}

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
