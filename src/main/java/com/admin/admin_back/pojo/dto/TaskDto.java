package com.admin.admin_back.pojo.dto;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * @author 陈群矜
 */
public class TaskDto {

    private Integer id;

    private String taskId;

    private Integer taskStatus;

    private Integer taskSuccessInsert;

    private Integer taskSuccessUpdate;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    public TaskDto() {}

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

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskSuccessInsert() {
        return taskSuccessInsert;
    }

    public void setTaskSuccessInsert(Integer taskSuccessInsert) {
        this.taskSuccessInsert = taskSuccessInsert;
    }

    public Integer getTaskSuccessUpdate() {
        return taskSuccessUpdate;
    }

    public void setTaskSuccessUpdate(Integer taskSuccessUpdate) {
        this.taskSuccessUpdate = taskSuccessUpdate;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
