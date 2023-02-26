package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author 陈群矜
 */
@ApiModel
public class TeacherVo {

    @ApiModelProperty("教工号")
    private String empNo;

    @ApiModelProperty("教工姓名")
    private String empName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("身份证")
    private String idNo;

    @ApiModelProperty("婚姻状况")
    private String marriage;

    @ApiModelProperty("编制类别")
    private String orgType;

    @ApiModelProperty("教职工类别")
    private String staffType;

    @ApiModelProperty("教师职称")
    private String title;

    @ApiModelProperty("文化程度")
    private String degree;

    @ApiModelProperty("职业等级")
    private String titleLevel;

    @ApiModelProperty("教师类别")
    private String teachType;

    @ApiModelProperty("教师所属单位")
    private String school;

    @ApiModelProperty("来校日期")
    private Date enterDate;

    @ApiModelProperty("从教年月")
    private String teachDate;

    @ApiModelProperty("当前状态")
    private String status;

    @ApiModelProperty("职级")
    private Integer rank;

    @ApiModelProperty("技术职务")
    private String tecposition;

    @ApiModelProperty("备注")
    private String memo;

    @ApiModelProperty("单位编号")
    private Integer schoolId;

    public TeacherVo() {}

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel) {
        this.titleLevel = titleLevel;
    }

    public String getTeachType() {
        return teachType;
    }

    public void setTeachType(String teachType) {
        this.teachType = teachType;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public String getTeachDate() {
        return teachDate;
    }

    public void setTeachDate(String teachDate) {
        this.teachDate = teachDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTecposition() {
        return tecposition;
    }

    public void setTecposition(String tecposition) {
        this.tecposition = tecposition;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
