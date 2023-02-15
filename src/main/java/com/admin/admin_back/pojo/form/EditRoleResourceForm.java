package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author 陈群矜
 */
public class EditRoleResourceForm {

    private String roleId;

    private String systemId;

    private List<String> resourceIds;

    public EditRoleResourceForm() {}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
