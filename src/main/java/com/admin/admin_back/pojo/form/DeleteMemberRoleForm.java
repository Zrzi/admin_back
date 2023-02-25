package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("删除用户角色关联")
public class DeleteMemberRoleForm {

    @ApiModelProperty("角色编码")
    private String roleId;

    @ApiModelProperty("用户名")
    private String userNo;

    public DeleteMemberRoleForm() {}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

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
