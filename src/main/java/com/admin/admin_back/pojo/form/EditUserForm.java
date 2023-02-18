package com.admin.admin_back.pojo.form;

import com.admin.admin_back.pojo.vo.StudentVo;
import com.admin.admin_back.pojo.vo.TeacherVo;
import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class EditUserForm {

    private Boolean isStudent;

    private String userNo;

    private StudentVo student;

    private TeacherVo teacher;

    public EditUserForm() {}

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public StudentVo getStudent() {
        return this.student;
    }

    public void setStudent(StudentVo student) {
        this.student = student;
    }

    public TeacherVo getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherVo teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
