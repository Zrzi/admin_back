package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author 陈群矜
 */
@ApiModel("添加用户角色关联信息")
public class AddMemberRoleForm {

    @ApiModelProperty("角色编码")
    private String roleId;

    @ApiModelProperty("用户名列表")
    private List<String> userNos;

    public AddMemberRoleForm() {}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getUserNos() {
        return userNos;
    }

    public void setUserNos(List<String> userNos) {
        this.userNos = userNos;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
