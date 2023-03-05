package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("Excel列名映射配置")
public class ExcelColumnVo {

    @ApiModelProperty("关联Excel映射配置编码")
    private String excelId;

    @ApiModelProperty("Excel列名")
    private String excelColumn;

    @ApiModelProperty("sql列名")
    private String sqlColumn;

    @ApiModelProperty("是否是主键")
    private Boolean isPrimaryKey;

    public ExcelColumnVo() {}

    public String getExcelId() {
        return excelId;
    }

    public void setExcelId(String excelId) {
        this.excelId = excelId;
    }

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

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
