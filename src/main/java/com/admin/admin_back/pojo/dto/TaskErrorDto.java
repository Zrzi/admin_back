package com.admin.admin_back.pojo.dto;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * @author 陈群矜
 */
public class TaskErrorDto {

    private Integer id;

    private String taskId;

    private String errorMessage;

    private String createdBy;

    private Date createdDate;

    public TaskErrorDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
