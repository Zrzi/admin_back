package com.admin.admin_back.pojo.form;

import com.admin.admin_back.pojo.vo.StudentVo;
import com.admin.admin_back.pojo.vo.TeacherVo;
import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * @author 陈群矜
 */
public class AddUserForm {

    private Boolean isStudent;

    private StudentVo student;

    private TeacherVo teacher;

    public AddUserForm() {}

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
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
