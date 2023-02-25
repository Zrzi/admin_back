package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author 陈群矜
 */
@ApiModel("编辑角色资源关联信息")
public class EditRoleResourceForm {

    @ApiModelProperty("角色编码")
    private String roleId;

    @ApiModelProperty("系统编码")
    private String systemId;

    @ApiModelProperty("资源编码列表")
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
