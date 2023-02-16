package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.PasswordException;
import com.admin.admin_back.pojo.exception.UserExistException;
import com.admin.admin_back.pojo.exception.UserNoException;
import com.admin.admin_back.pojo.form.AddUserForm;
import com.admin.admin_back.pojo.form.LoginForm;
import com.admin.admin_back.pojo.form.ResetPasswordForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.StudentVo;
import com.admin.admin_back.pojo.vo.TeacherVo;
import com.admin.admin_back.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @CheckRole("resetPassword")
    @PostMapping("/resetPassword")
    public Result<?> resetPassword(ResetPasswordForm resetPasswordForm) {
        String userNo = UserThreadLocal.getUser().getUserNo();
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

    @CheckRole("getUser")
    @GetMapping("/user/get")
    public Result<?> getUser() {
        return new Result<>(ResponseMessage.SUCCESS, userService.getUsers());
    }

    @CheckRole("addUser")
    @PostMapping("/user/post")
    public Result<?> addUser(AddUserForm addUserForm) {
        String flag = checkAddUserForm(addUserForm);
        if (StringUtils.isBlank(flag)) {
            return new Result<>(ResponseMessage.MEMBER_FORM_ERROR, flag);
        }
        try {
            userService.addUser(addUserForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (UserNoException exception) {
            return new Result<>(ResponseMessage.MEMBER_FORM_ERROR, exception.getMessage());
        }
    }

    @PostMapping("/user/delete")
    public Result<?> removeUser(String userNo) {
        try {
            userService.deleteUser(userNo);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (UserExistException exception) {
            return new Result<>(ResponseMessage.USER_NOT_FOUND);
        }
    }

    private String checkAddUserForm(AddUserForm addUserForm) {
        boolean isStudent = addUserForm.getIsStudent();
        if (isStudent) {
            return checkAddStudent(addUserForm.getStudent());
        } else {
            return checkAddTeacher(addUserForm.getTeacher());
        }
    }

    private String checkAddStudent(@Nullable StudentVo studentVo) {
        if (Objects.isNull(studentVo)) {
            return "上传数据为空";
        }
        return "";
    }

    private String checkAddTeacher(@Nullable TeacherVo teacherVo) {
        if (Objects.isNull(teacherVo)) {
            return "上传数据为空";
        }
        return "";
    }

}
