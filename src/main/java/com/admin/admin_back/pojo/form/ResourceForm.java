package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author 陈群矜
 */
public class ResourceForm {

    private String resourceId;

    private String resourceName;

    private String systemId;

    private String resourceUrl;

    private String resourceType;

    public ResourceForm() {}

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
