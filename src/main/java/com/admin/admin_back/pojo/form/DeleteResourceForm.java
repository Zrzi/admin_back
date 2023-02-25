package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("删除资源相关信息")
public class DeleteResourceForm {

    @ApiModelProperty("资源编码")
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
