package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author 陈群矜
 */
@ApiModel("Excel映射配置返回结果")
public class ExcelVo {

    @ApiModelProperty("Excel映射配置编码")
    private String excelId;

    @ApiModelProperty("Excel表格名称")
    private String excelName;

    @ApiModelProperty("sql表名称")
    private String sqlName;

    @ApiModelProperty("Excel映射列名配置")
    private List<ExcelColumnVo> rows;

    public ExcelVo() {}

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

    public List<ExcelColumnVo> getRows() {
        return rows;
    }

    public void setRows(List<ExcelColumnVo> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
