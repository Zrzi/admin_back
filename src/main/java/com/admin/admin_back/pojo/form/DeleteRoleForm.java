package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class DeleteRoleForm {

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
