package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author 陈群矜
 */
@ApiModel("资源信息")
public class ResourceForm {

    @ApiModelProperty(value = "资源编码，添加时不需要提供")
    private String resourceId;

    @ApiModelProperty(value = "资源名称", required = true)
    private String resourceName;

    @ApiModelProperty(value = "系统编码", required = true)
    private String systemId;

    @ApiModelProperty(value = "资源路径", required = true)
    private String resourceUrl;

    @ApiModelProperty(value = "资源类型", required = true)
    private String resourceType;

    @ApiModelProperty(value = "是否是导航菜单")
    private boolean isMenu;

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

    public boolean getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(boolean isMenu) {
        this.isMenu = isMenu;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
