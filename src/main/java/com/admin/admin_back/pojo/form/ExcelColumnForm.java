package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("Excel列映射配置")
public class ExcelColumnForm {

    @ApiModelProperty(value = "Excel列名", required = true)
    private String excelColumn;

    @ApiModelProperty(value = "sql列名", required = true)
    private String sqlColumn;

    @ApiModelProperty(value = "是否需要特殊处理", required = true)
    private boolean isSpecial;

    public ExcelColumnForm() {}

    public String getExcelColumn() {
        return excelColumn;
    }

    public void setExcelColumn(String excelColumn) {
        this.excelColumn = excelColumn;
    }

    public String getSqlColumn() {
        return sqlColumn;
    }

    public void setSqlColumn(String sqlColumn) {
        this.sqlColumn = sqlColumn;
    }

    public boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
