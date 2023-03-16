package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author 陈群矜
 */
@ApiModel("查询excel上传结果")
public class ExcelTaskVo {

    @ApiModelProperty("任务状态，0：运行中；1：成功；2：异常")
    private int taskStatus;

    @ApiModelProperty("异常信息列表")
    private List<String> errorMessage;

    @ApiModelProperty("成功插入数据个数")
    private int taskSuccessInsert;

    @ApiModelProperty("成功修改数据个数")
    private int taskSuccessUpdate;

    public ExcelTaskVo() {}

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getTaskSuccessInsert() {
        return taskSuccessInsert;
    }

    public void setTaskSuccessInsert(int taskSuccessInsert) {
        this.taskSuccessInsert = taskSuccessInsert;
    }

    public int getTaskSuccessUpdate() {
        return taskSuccessUpdate;
    }

    public void setTaskSuccessUpdate(int taskSuccessUpdate) {
        this.taskSuccessUpdate = taskSuccessUpdate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
