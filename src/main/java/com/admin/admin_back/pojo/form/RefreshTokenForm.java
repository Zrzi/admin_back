package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("刷新token请求参数")
public class RefreshTokenForm {

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

    public RefreshTokenForm() {}

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
