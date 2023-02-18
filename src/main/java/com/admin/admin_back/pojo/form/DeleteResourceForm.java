package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

/**
 * author 陈群矜
 */
public class DeleteResourceForm {

    private String resourceId;

    public DeleteResourceForm() {}

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
