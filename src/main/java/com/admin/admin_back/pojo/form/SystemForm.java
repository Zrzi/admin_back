package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("系统信息")
public class SystemForm {

    @ApiModelProperty("系统id：添加时不需要；修改时需要")
    private String systemId;

    @ApiModelProperty(value = "系统名称", required = true)
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
