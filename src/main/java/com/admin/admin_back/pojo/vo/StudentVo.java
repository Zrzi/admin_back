package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("学生信息")
public class StudentVo {

    @ApiModelProperty("学号")
    private String stuNo;

    @ApiModelProperty("学生姓名")
    private String stuName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("专业号")
    private String majorNo;

    @ApiModelProperty("班号")
    private String classNo;

    @ApiModelProperty("当前级")
    private Integer curGrade;

    @ApiModelProperty("入学年份")
    private Integer enterYear;

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("身份证")
    private String id;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("民族")
    private String nation;

    @ApiModelProperty("政治面貌")
    private String politicalStatus;

    @ApiModelProperty("生源所在地")
    private String sourcePlace;

    public StudentVo() {}

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Integer getCurGrade() {
        return curGrade;
    }

    public void setCurGrade(Integer curGrade) {
        this.curGrade = curGrade;
    }

    public Integer getEnterYear() {
        return enterYear;
    }

    public void setEnterYear(Integer enterYear) {
        this.enterYear = enterYear;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getSourcePlace() {
        return sourcePlace;
    }

    public void setSourcePlace(String sourcePlace) {
        this.sourcePlace = sourcePlace;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
