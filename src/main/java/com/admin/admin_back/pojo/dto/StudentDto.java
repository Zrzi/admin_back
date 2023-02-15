package com.admin.admin_back.pojo.dto;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class StudentDto {

    private String stuNo;

    private String name;

    private String majorNo;

    private String classNo;

    public StudentDto() {}

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajorNo() {
        return majorNo;
    }

    public void setMajorNo(String majorNo) {
        this.majorNo = majorNo;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
