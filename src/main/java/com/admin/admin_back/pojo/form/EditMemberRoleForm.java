package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class EditMemberRoleForm {

    private String uesrNo;

    private String roleId;

    private Integer level;

    public EditMemberRoleForm() {}

    public String getUesrNo() {
        return uesrNo;
    }

    public void setUesrNo(String uesrNo) {
        this.uesrNo = uesrNo;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
