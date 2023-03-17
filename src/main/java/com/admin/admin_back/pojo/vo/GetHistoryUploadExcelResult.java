package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈群矜
 */
@ApiModel("获取历史文件上传记录")
public class GetHistoryUploadExcelResult {

    @ApiModelProperty("总记录数")
    private int total;

    @ApiModelProperty("分页数据")
    private final List<ExcelTaskVo> excelTasks = new ArrayList<>();

    public GetHistoryUploadExcelResult() {}

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ExcelTaskVo> getExcelTasks() {
        return excelTasks;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
