package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.AddUserForm;
import com.admin.admin_back.pojo.vo.UsersVo;

/**
 * @author 陈群矜
 */
public interface UserService {

    String login(String userNo, String password);

    void resetPassword(String userNo, String oldPassword, String newPassword);

    UsersVo getUsers();

    void addUser(AddUserForm addUserForm);

}
