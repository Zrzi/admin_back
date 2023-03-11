package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * getSqlColumns接口返回值
 * @author 陈群矜
 */
@ApiModel("getSqlColumns接口返回值")
public class GetSqlColumnsVo {

    @ApiModelProperty("非空字段列表")
    private final List<String> nonNullList = new ArrayList<>();

    @ApiModelProperty("可以为空字段列表")
    private final List<String> nullableList = new ArrayList<>();

    public GetSqlColumnsVo() {}

    public List<String> getNonNullList() {
        return nonNullList;
    }

    public List<String> getNullableList() {
        return nullableList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
