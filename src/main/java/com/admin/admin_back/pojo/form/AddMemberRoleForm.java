package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author 陈群矜
 */
public class AddMemberRoleForm {

    private String roleId;

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
