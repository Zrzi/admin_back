package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author 陈群矜
 */
@ApiModel("Excel映射配置请求")
public class ExcelForm {

    @ApiModelProperty(value = "Excel映射配置编码，新增时不需要传")
    private String excelId;

    @ApiModelProperty(value = "Excel表格名称", required = true)
    private String excelName;

    @ApiModelProperty(value = "sql表格名称", required = true)
    private String sqlName;

    @ApiModelProperty(value = "Excel列映射配置列表", required = true)
    private List<ExcelColumnForm> rows;

    public ExcelForm() {}

    public String getExcelId() {
        return excelId;
    }

    public void setExcelId(String excelId) {
        this.excelId = excelId;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public List<ExcelColumnForm> getRows() {
        return rows;
    }

    public void setRows(List<ExcelColumnForm> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
