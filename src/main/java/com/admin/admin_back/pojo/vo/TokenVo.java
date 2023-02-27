package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class TokenVo {

    private String token;

    private String refreshToken;

    public TokenVo() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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
