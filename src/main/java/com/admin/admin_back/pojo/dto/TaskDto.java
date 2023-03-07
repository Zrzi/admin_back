package com.admin.admin_back.pojo.dto;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class TaskDto {

    private Integer id;

    private String taskId;

    private Integer taskStatus;

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
