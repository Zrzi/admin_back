package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.enums.UserTypeEnum;
import com.admin.admin_back.pojo.exception.PasswordException;
import com.admin.admin_back.pojo.exception.UserExistException;
import com.admin.admin_back.pojo.exception.UserNoException;
import com.admin.admin_back.pojo.form.AddUserForm;
import com.admin.admin_back.pojo.form.DeleteUserForm;
import com.admin.admin_back.pojo.form.LoginForm;
import com.admin.admin_back.pojo.form.ResetPasswordForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.StudentVo;
import com.admin.admin_back.pojo.vo.TeacherVo;
import com.admin.admin_back.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public Result<Map<String, String>> login(@RequestBody LoginForm loginForm) {
        String userNo = loginForm.getUserNo();
        String password = loginForm.getPassword();
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
            return new Result<>(ResponseMessage.USER_NOT_FOUND);
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
    public Result<?> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm) {
        String userNo = UserThreadLocal.getUser().getUserNo();
        String oldPassword = resetPasswordForm.getOldPassword();
        String newPassword = resetPasswordForm.getNewPassword();
        if (StringUtils.isBlank(userNo)) {
            return new Result<>(ResponseMessage.USER_NO_NOT_FOUND);
        }
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return new Result<>(ResponseMessage.PASSWORD_NOT_FOUND);
        }
        oldPassword = oldPassword.trim();
        newPassword = newPassword.trim();
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
    public Result<?> getUser(@RequestParam("userType") String userType,
                             @RequestParam("start") Integer start,
                             @RequestParam("pageSize") Integer pageSize) {
        UserTypeEnum userTypeEnum = UserTypeEnum.findUserTypeByMessage(userType);
        if (Objects.isNull(userTypeEnum)) {
            return new Result<>(ResponseMessage.USER_TYOE_ERROR);
        }
        if (start == null || start < 0) {
            start = 0;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }
        return new Result<>(ResponseMessage.SUCCESS, userService.getUsersPage(userTypeEnum, start, pageSize));
    }

    @CheckRole("addUser")
    @PostMapping("/user/post")
    public Result<?> addUser(@RequestBody AddUserForm addUserForm) {
        String flag = checkAddUserForm(addUserForm);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.MEMBER_FORM_ERROR, flag);
        }
        try {
            userService.addUser(addUserForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (UserNoException exception) {
            return new Result<>(ResponseMessage.MEMBER_FORM_ERROR, exception.getMessage());
        }
    }

    @CheckRole("removeUser")
    @PostMapping("/user/delete")
    public Result<?> removeUser(@RequestBody DeleteUserForm deleteUserForm) {
        String userNo = deleteUserForm.getUserNo();
        if (StringUtils.isBlank(userNo)) {
            return new Result<>(ResponseMessage.USER_NO_NOT_FOUND);
        }
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
        String stuNo = studentVo.getStuNo();
        if (StringUtils.isBlank(stuNo)) {
            return "请输入学生学号";
        }
        stuNo = stuNo.trim();
        if (stuNo.length() > 20) {
            return "学号长度不超过20个字符";
        }
        studentVo.setStuNo(stuNo);
        String stuName = studentVo.getStuName();
        if (StringUtils.isBlank(stuName)) {
            return "请输入学生姓名";
        }
        stuName = stuName.trim();
        if (stuName.length() > 20) {
            return "学生姓名长度不超过50个字符";
        }
        studentVo.setStuName(stuName);
        String sex = studentVo.getSex();
        if (StringUtils.isBlank(sex)) {
            return "请输入学生性别";
        }
        sex = sex.trim();
        if (sex.length() > 5) {
            return "性别长度不超过5个字符";
        }
        studentVo.setSex(sex);
        String majorNo = studentVo.getMajorNo();
        if (StringUtils.isBlank(majorNo)) {
            return "请输入学生专业号";
        }
        majorNo = majorNo.trim();
        if (majorNo.length() > 10) {
            return "学生专业号长度不超过10个字符";
        }
        studentVo.setMajorNo(majorNo);
        String classNo = studentVo.getClassNo();
        if (StringUtils.isBlank(classNo)) {
            return "请输入学生班号";
        }
        classNo = classNo.trim();
        if (classNo.length() > 20) {
            return "学生班号长度不超过20个字符";
        }
        studentVo.setClassNo(classNo);
        Integer curGrade = studentVo.getCurGrade();
        if (Objects.isNull(curGrade)) {
            return "请输入学生当前所在级";
        }
        if (curGrade <= 0) {
            return "当前所在级必须是正数";
        }
        Integer enterYear = studentVo.getEnterYear();
        if (Objects.isNull(enterYear)) {
            return "请输入学生入学年份";
        }
        if (enterYear <= 0) {
            return "入学年份必须是正数";
        }
        String birthday = studentVo.getBirthday();
        if (StringUtils.isBlank(birthday)) {
            return "请输入学生出生日期";
        }
        birthday = birthday.trim();
        if (birthday.length() > 20) {
            return "出生日期长度不超过20个字符";
        }
        studentVo.setBirthday(birthday);
        String ID = studentVo.getID();
        if (StringUtils.isNotBlank(ID)) {
            ID = ID.trim();
            if (ID.length() > 20) {
                return "学生身份证最长20位";
            }
            studentVo.setID(ID);
        }
        String status = studentVo.getStatus();
        if (StringUtils.isBlank(status)) {
            return "请输入学生在校状态";
        }
        status = status.trim();
        if (status.length() > 10) {
            return "在校状态不超过10个字符";
        }
        studentVo.setStatus(status);
        String nation = studentVo.getNation();
        if (StringUtils.isBlank(nation)) {
            return "请输入学生民族";
        }
        nation = nation.trim();
        if (nation.length() > 10) {
            return "民族不超过10个字符";
        }
        studentVo.setNation(nation);
        String politicalStatus = studentVo.getPoliticalStatus();
        if (StringUtils.isBlank(politicalStatus)) {
            return "请输入学生政治面貌";
        }
        politicalStatus = politicalStatus.trim();
        if (politicalStatus.length() > 20) {
            return "政治面貌不超过20个字符";
        }
        studentVo.setPoliticalStatus(politicalStatus);
        String sourcePlace = studentVo.getSourcePlace();
        if (StringUtils.isNotBlank(sourcePlace)) {
            sourcePlace = sourcePlace.trim();
            if (sourcePlace.length() > 50) {
                return "生源所在地不超过50个字符";
            }
            studentVo.setSourcePlace(sourcePlace);
        }
        return "";
    }

    private String checkAddTeacher(@Nullable TeacherVo teacherVo) {
        if (Objects.isNull(teacherVo)) {
            return "上传数据为空";
        }
        String empNo = teacherVo.getEmpNo();
        if (StringUtils.isBlank(empNo)) {
            return "请输入教工号";
        }
        empNo = empNo.trim();
        if (empNo.length() > 20) {
            return "教工号长度不超过20个字符";
        }
        teacherVo.setEmpNo(empNo);
        String empName = teacherVo.getEmpName();
        if (StringUtils.isBlank(empName)) {
            return "请输入姓名";
        }
        empName = empName.trim();
        if (empName.length() > 50) {
            return "姓名长度不超过50个字符";
        }
        teacherVo.setEmpName(empName);
        String sex = teacherVo.getSex();
        if (StringUtils.isBlank(sex)) {
            return "请输入性别";
        }
        sex = sex.trim();
        if (sex.length() > 20) {
            return "性别不超过20个字符";
        }
        teacherVo.setSex(sex);
        Date birthday = teacherVo.getBirthday();
        if (Objects.isNull(birthday)) {
            return "请选择出生日期";
        }
        String phone = teacherVo.getPhone();
        if (StringUtils.isNotBlank(phone)) {
            phone = phone.trim();
            if (phone.length() > 15) {
                return "电话长度不超过15个字符";
            }
            teacherVo.setPhone(phone);
        }
        String IDNo = teacherVo.getIDNo();
        if (StringUtils.isNotEmpty(IDNo)) {
            IDNo = IDNo.trim();
            if (IDNo.length() > 20) {
                return "身份证长度不超过20个字符";
            }
            teacherVo.setIDNo(IDNo);
        }
        String marriage = teacherVo.getMarriage();
        if (StringUtils.isNotEmpty(marriage)) {
            marriage = marriage.trim();
            if (marriage.length() > 10) {
                return "婚姻状况长度不超过10个字符";
            }
            teacherVo.setMarriage(marriage);
        }
        String orgType = teacherVo.getOrgType();
        if (StringUtils.isNotEmpty(orgType)) {
            orgType = orgType.trim();
            if (orgType.length() > 20) {
                return "编制类别长度不超过20个字符";
            }
            teacherVo.setOrgType(orgType);
        }
        String staffType = teacherVo.getStaffType();
        if (StringUtils.isNotEmpty(staffType)) {
            staffType = staffType.trim();
            if (staffType.length() > 10) {
                return "教职工类别长度不超过10个字符";
            }
            teacherVo.setStaffType(staffType);
        }
        String title = teacherVo.getTitle();
        if (StringUtils.isNotEmpty(title)) {
            title = title.trim();
            if (title.length() > 20) {
                return "教师职称长度不超过20个字符";
            }
            teacherVo.setTitle(title);
        }
        String degree = teacherVo.getDegree();
        if (StringUtils.isNotEmpty(degree)) {
            degree = degree.trim();
            if (degree.length() > 10) {
                return "文化程度长度不超过10个字符";
            }
            teacherVo.setDegree(degree);
        }
        String titleLevel = teacherVo.getTitleLevel();
        if (StringUtils.isNotEmpty(titleLevel)) {
            titleLevel = titleLevel.trim();
            if (titleLevel.length() > 10) {
                return "职称等级长度不超过10个字符";
            }
            teacherVo.setTitleLevel(titleLevel);
        }
        String teachType = teacherVo.getTeachType();
        if (StringUtils.isNotEmpty(teachType)) {
            teachType = teachType.trim();
            if (teachType.length() > 10) {
                return "教师类别长度不超过10个字符";
            }
            teacherVo.setTeachType(teachType);
        }
        String school = teacherVo.getSchool();
        if (StringUtils.isNotEmpty(school)) {
            school = school.trim();
            if (school.length() > 10) {
                return "教师所属单位长度不超过10个字符";
            }
            teacherVo.setSchool(school);
        }
        Date enterDate = teacherVo.getEnterDate();
        String teachDate = teacherVo.getTeachDate();
        if (StringUtils.isNotEmpty(teachDate)) {
            teachDate = teachType.trim();
            if (teachDate.length() > 10) {
                return "从教年月长度不超过10个字符";
            }
            teacherVo.setTeachDate(teachDate);
        }
        String status = teacherVo.getStatus();
        if (StringUtils.isNotBlank(status)) {
            status = status.trim();
            if (status.length() > 10) {
                return "当前状态长度不超过10个字符";
            }
            teacherVo.setStatus(status);
        }
        Integer rank = teacherVo.getRank();
        String tecposition = teacherVo.getTecposition();
        if (StringUtils.isNotBlank(tecposition)) {
            tecposition = tecposition.trim();
            if (tecposition.length() > 20) {
                return "技术职务长度不超过20个字符";
            }
            teacherVo.setTecposition(tecposition);
        }
        String memo = teacherVo.getMemo();
        if (StringUtils.isNotEmpty(memo)) {
            memo = memo.trim();
            if (memo.length() > 100) {
                return "备注长度不超过100个字符";
            }
            teacherVo.setMemo(memo);
        }
        Integer schoolId = teacherVo.getSchoolId();
        return "";
    }

}
