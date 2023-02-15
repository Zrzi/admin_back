package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

public class SystemForm {

    private String systemId;

    private String systemName;

    public SystemForm() {}

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
