package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class UserVo {

    private String userNo;

    private String username;

    public UserVo() {}

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
