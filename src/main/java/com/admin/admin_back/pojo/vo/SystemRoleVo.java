package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
public class SystemRoleVo {

    private String systemId;

    private String systemName;

    private List<RoleVo> roles;

    public SystemRoleVo() {}

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public List<RoleVo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVo> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
