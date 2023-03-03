package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("删除Excel映射配置请求参数")
public class DeleteExcelForm {

    @ApiModelProperty(value = "Excel映射配置编码", required = true)
    private String excelId;

    public DeleteExcelForm() {}

    public String getExcelId() {
        return excelId;
    }

    public void setExcelId(String excelId) {
        this.excelId = excelId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
