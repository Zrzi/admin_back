package com.admin.admin_back.service;

/**
 * @author 陈群矜
 */
public interface UserService {

    String login(String userNo, String password);

    void resetPassword(String userNo, String oldPassword, String newPassword);

}
