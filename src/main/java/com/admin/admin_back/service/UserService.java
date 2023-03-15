package com.admin.admin_back.service;

import com.admin.admin_back.pojo.enums.UserTypeEnum;
import com.admin.admin_back.pojo.event.ExcelEvent;
import com.admin.admin_back.pojo.form.AddUserForm;
import com.admin.admin_back.pojo.form.EditUserForm;
import com.admin.admin_back.pojo.vo.*;

import java.util.List;

/**
 * @author 陈群矜
 */
public interface UserService {

    TokenVo login(String userNo, String password);

    void resetPassword(String userNo, String oldPassword, String newPassword);

    TokenVo refreshToken(String refreshToken);

    UsersVo getUsersPage(UserTypeEnum userTypeEnum, Integer start, Integer pageSize, String searchKey);

    StudentVo getStudentByStuNo(String userNo);

    TeacherVo getTeacherByEmpNo(String userNo);

    UsersVo getUsers();

    void addUser(AddUserForm addUserForm);

    void updateUser(EditUserForm editUserForm);

    void deleteUser(String userNo);

    List<UserVo> getUsersByRoleId(String roleId);

    void onListenExcel(ExcelEvent excelEvent);

}
