package com.admin.admin_back.controller;

import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.PasswordException;
import com.admin.admin_back.pojo.exception.UserExistException;
import com.admin.admin_back.pojo.form.LoginForm;
import com.admin.admin_back.pojo.form.ResetPasswordForm;
import com.admin.admin_back.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈群矜
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String, String>> login(LoginForm loginForm) {
        String userNo = loginForm.getUserNo().trim();
        String password = loginForm.getPassword().trim();
        if (StringUtils.isBlank(userNo)) {
            return new Result<>(ResponseMessage.USER_NO_NOT_FOUND);
        }
        if (StringUtils.isBlank(password)) {
            return new Result<>(ResponseMessage.PASSWORD_NOT_FOUND);
        }
        try {
            String token = userService.login(userNo, password);
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return new Result<>(ResponseMessage.SUCCESS, map);
        } catch (UserExistException exception) {
            return new Result<>(ResponseMessage.USER_NO_NOT_FOUND);
        } catch (PasswordException exception) {
            return new Result<>(ResponseMessage.PASSWORD_ERROR);
        }
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        return new Result<>(ResponseMessage.SUCCESS);
    }

    @PostMapping("/resetPassword")
    public Result<?> resetPassword(ResetPasswordForm resetPasswordForm) {
        String userNo = resetPasswordForm.getUserNo().trim();
        String oldPassword = resetPasswordForm.getOldPassword().trim();
        String newPassword = resetPasswordForm.getNewPassword().trim();
        if (StringUtils.isBlank(userNo)) {
            return new Result<>(ResponseMessage.USER_NO_NOT_FOUND);
        }
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return new Result<>(ResponseMessage.PASSWORD_NOT_FOUND);
        }
        try {
            userService.resetPassword(userNo, oldPassword, newPassword);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (UserExistException exception) {
            return new Result<>(ResponseMessage.USER_NO_NOT_FOUND);
        } catch (PasswordException exception) {
            return new Result<>(ResponseMessage.PASSWORD_ERROR);
        }
    }

}
