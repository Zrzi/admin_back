package com.admin.admin_back.pojo.form;

import com.admin.admin_back.pojo.vo.StudentVo;
import com.admin.admin_back.pojo.vo.TeacherVo;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author 陈群矜
 */
@ApiModel("添加用户相关信息")
public class AddUserForm {

    @ApiModelProperty("是否是学生类型")
    private Boolean isStudent;

    @ApiModelProperty("学生信息")
    private StudentVo student;

    @ApiModelProperty("教工信息")
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
