package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author 陈群矜
 */
@ApiModel("角色信息")
public class RoleForm {

    @ApiModelProperty("角色编码，修改角色时需要提供")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("系统编码，添加角色时需要提供")
    private String systemId;

    public RoleForm() {}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
