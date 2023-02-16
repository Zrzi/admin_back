package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author 陈群矜
 */
public class UsersVo {

    private List<UserVo> adminUsers;

    private List<StudentVo> students;

    private List<TeacherVo> teachers;

    public UsersVo() {}

    public List<UserVo> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(List<UserVo> adminUsers) {
        this.adminUsers = adminUsers;
    }

    public List<StudentVo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentVo> students) {
        this.students = students;
    }

    public List<TeacherVo> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherVo> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
