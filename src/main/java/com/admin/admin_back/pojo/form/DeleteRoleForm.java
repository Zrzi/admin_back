package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("删除角色信息")
public class DeleteRoleForm {

    @ApiModelProperty("角色编码")
    private String roleId;

    public DeleteRoleForm() {}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
