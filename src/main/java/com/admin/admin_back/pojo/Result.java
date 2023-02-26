package com.admin.admin_back.pojo;

import com.admin.admin_back.pojo.common.ResponseMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @param <T> data的类型
 * @author 陈群矜
 */
@ApiModel
public class Result<T> {

    @ApiModelProperty("返回编码")
    private int code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    public Result(ResponseMessage responseMessage) {
        this.code = responseMessage.code;
        this.message = responseMessage.message;
        this.data = null;
    }

    public Result(ResponseMessage responseMessage, String message) {
        this.code = responseMessage.code;
        this.message = message;
        this.data = null;
    }

    public Result(ResponseMessage responseMessage, T data) {
        this.code = responseMessage.code;
        this.message = responseMessage.message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
