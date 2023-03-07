package com.admin.admin_back.pojo.dto;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class TaskErrorDto {

    private Integer id;

    private String taskId;

    private String errorMessage;

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
