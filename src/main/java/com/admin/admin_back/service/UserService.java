package com.admin.admin_back.service;

import com.admin.admin_back.pojo.enums.UserTypeEnum;
import com.admin.admin_back.pojo.form.AddUserForm;
import com.admin.admin_back.pojo.form.EditUserForm;
import com.admin.admin_back.pojo.vo.StudentVo;
import com.admin.admin_back.pojo.vo.TeacherVo;
import com.admin.admin_back.pojo.vo.TokenVo;
import com.admin.admin_back.pojo.vo.UsersVo;

/**
 * @author 陈群矜
 */
public interface UserService {

    TokenVo login(String userNo, String password);

    void resetPassword(String userNo, String oldPassword, String newPassword);

    TokenVo refreshToken(String refreshToken);

    UsersVo getUsersPage(UserTypeEnum userTypeEnum, Integer start, Integer pageSize);

    StudentVo getStudentByStuNo(String userNo);

    TeacherVo getTeacherByEmpNo(String userNo);

    UsersVo getUsers();

    void addUser(AddUserForm addUserForm);

    void updateUser(EditUserForm editUserForm);

    void deleteUser(String userNo);

}
