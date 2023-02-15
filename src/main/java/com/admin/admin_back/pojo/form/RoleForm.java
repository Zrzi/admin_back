package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author 陈群矜
 */
public class RoleForm {

    private String roleId;

    private String roleName;

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
