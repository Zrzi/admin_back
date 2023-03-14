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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
