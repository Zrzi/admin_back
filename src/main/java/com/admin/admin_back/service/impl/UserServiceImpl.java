package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.StudentMapper;
import com.admin.admin_back.mapper.TeacherMapper;
import com.admin.admin_back.mapper.UserMapper;
import com.admin.admin_back.mapper.UserRoleMapper;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.StudentDto;
import com.admin.admin_back.pojo.dto.TeacherDto;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.dto.UserRoleDto;
import com.admin.admin_back.pojo.enums.UserTypeEnum;
import com.admin.admin_back.pojo.event.ExcelEvent;
import com.admin.admin_back.pojo.exception.PasswordException;
import com.admin.admin_back.pojo.exception.UserExistException;
import com.admin.admin_back.pojo.exception.UserNoException;
import com.admin.admin_back.pojo.form.AddUserForm;
import com.admin.admin_back.pojo.form.EditUserForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.*;
import com.admin.admin_back.service.UserService;
import com.admin.admin_back.utils.JwtTokenUtil;
import com.admin.admin_back.utils.Md5Util;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 陈群矜
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public TokenVo login(String userNo, String password) {
        UserDto user = userMapper.findUserByUserNo(userNo);
        if (Objects.isNull(user)) {
            throw new UserExistException();
        }
        boolean checkPassword = Md5Util.verify(password, user.getPassword());
        if (!checkPassword) {
            throw new PasswordException();
        }
        return generateTokenVo(user);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resetPassword(String userNo, String oldPassword, String newPassword) {
        UserDto user = userMapper.findUserByUserNo(userNo);
        if (Objects.isNull(user)) {
            throw new UserExistException();
        }
        boolean checkPassword = Md5Util.verify(oldPassword, user.getPassword());
        if (!checkPassword) {
            throw new PasswordException();
        }
        user.setPassword(Md5Util.encrypt(newPassword));
        user.setUpdatedBy(userNo);
        userMapper.updateUser(user);
    }

    @Override
    public TokenVo refreshToken(String refreshToken) {
        String userNo = jwtTokenUtil.getUserNoFromToken(refreshToken);
        UserDto user = userMapper.findUserByUserNo(userNo);
        if (Objects.isNull(user)) {
            throw new UserExistException();
        }
        return generateTokenVo(user);
    }

    private TokenVo generateTokenVo(UserDto user) {
        TokenVo tokenVo = new TokenVo();
        String userNo = user.getUserNo();
        String jwtToken = null;
        String refreshToken = null;
        List<UserRoleDto> dtos = userRoleMapper.findUserRoleByNo(userNo);
        if (CollectionUtils.isEmpty(dtos)) {
            jwtToken = jwtTokenUtil.generateToken(user, new ArrayList<>(), false);
            refreshToken = jwtTokenUtil.generateToken(user, new ArrayList<>(), true);
        } else {
            List<UserRoleVo> userRoleVos = new ArrayList<>();
            for (UserRoleDto dto : dtos) {
                UserRoleVo vo = new UserRoleVo();
                vo.setUserNo(dto.getUserNo());
                vo.setRoleId(dto.getRoleId());
                vo.setLevel(dto.getLevel());
                vo.setUserType(Objects.requireNonNull(UserTypeEnum.findUserTypeEnumByCode(dto.getUserType())).message);
                vo.setCreatedBy(dto.getCreatedBy());
                vo.setCreatedDate(dto.getCreatedDate());
                vo.setUpdatedBy(dto.getUpdatedBy());
                vo.setUpdatedDate(dto.getUpdatedDate());
                userRoleVos.add(vo);
            }
            jwtToken = jwtTokenUtil.generateToken(user, userRoleVos, false);
            refreshToken = jwtTokenUtil.generateToken(user, userRoleVos, true);
        }
        // jwt token过期时间是5分钟，所以在redis中缓存大于5分钟即可，这里设置为10分钟
        redisTemplate.opsForValue().set(userNo, Md5Util.digest(jwtToken), 600, TimeUnit.SECONDS);
        tokenVo.setToken(jwtToken);
        tokenVo.setRefreshToken(refreshToken);
        return tokenVo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public StudentVo getStudentByStuNo(String userNo) {
        StudentDto studentDto = studentMapper.findStudentByStuNo(userNo);
        return JSON.parseObject(JSON.toJSONString(studentDto), StudentVo.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public TeacherVo getTeacherByEmpNo(String userNo) {
        TeacherDto teacherDto = teacherMapper.findTeacherByEmpNo(userNo);
        return JSON.parseObject(JSON.toJSONString(teacherDto), TeacherVo.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UsersVo getUsersPage(UserTypeEnum userTypeEnum, Integer start, Integer pageSize, String searchKey) {
        UsersVo usersVo = new UsersVo();
        switch (userTypeEnum) {
            case STUDENT:
                List<String> studentNos =
                        userMapper.findUserNoPageByUserType(UserTypeEnum.STUDENT.code, start, pageSize, searchKey);
                List<StudentVo> studentVos = new ArrayList<>();
                if (!CollectionUtils.isEmpty(studentNos)) {
                    for (String studentNo : studentNos) {
                        StudentDto studentDto = studentMapper.findStudentByStuNo(studentNo);
                        StudentVo studentVo = JSON.parseObject(JSON.toJSONString(studentDto), StudentVo.class);
                        studentVos.add(studentVo);
                    }
                }
                usersVo.setStudents(studentVos);
                usersVo.setTotal(userMapper.findUserCountByUserType(userTypeEnum.code, searchKey));
                break;
            case TEACHER:
                List<String> teacherNos =
                        userMapper.findUserNoPageByUserType(UserTypeEnum.TEACHER.code, start, pageSize, searchKey);
                List<TeacherVo> teacherVos = new ArrayList<>();
                if (!CollectionUtils.isEmpty(teacherNos)) {
                    for (String teacherNo : teacherNos) {
                        TeacherDto teacherDto = teacherMapper.findTeacherByEmpNo(teacherNo);
                        TeacherVo teacherVo = JSON.parseObject(JSON.toJSONString(teacherDto), TeacherVo.class);
                        teacherVos.add(teacherVo);
                    }
                }
                usersVo.setTeachers(teacherVos);
                usersVo.setTotal(userMapper.findUserCountByUserType(userTypeEnum.code, searchKey));
                break;
            default:
                // 不返回系统用户
                break;
        }
        return usersVo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UsersVo getUsers() {
        List<String> studentNos = userMapper.findUserNoByUserType(UserTypeEnum.STUDENT.code);
        List<StudentVo> studentVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(studentNos)) {
            for (String studentNo : studentNos) {
                StudentDto studentDto = studentMapper.findStudentByStuNo(studentNo);
                StudentVo studentVo = JSON.parseObject(JSON.toJSONString(studentDto), StudentVo.class);
                studentVos.add(studentVo);
            }
        }
        List<String> teacherNos = userMapper.findUserNoByUserType(UserTypeEnum.TEACHER.code);
        List<TeacherVo> teacherVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(teacherNos)) {
            for (String teacherNo : teacherNos) {
                TeacherDto teacherDto = teacherMapper.findTeacherByEmpNo(teacherNo);
                TeacherVo teacherVo = JSON.parseObject(JSON.toJSONString(teacherDto), TeacherVo.class);
                teacherVos.add(teacherVo);
            }
        }
        UsersVo result = new UsersVo();
        result.setStudents(studentVos);
        result.setTeachers(teacherVos);
        return result;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addUser(AddUserForm addUserForm) {
        if (addUserForm.getIsStudent()) {
            addStudent(addUserForm.getStudent());
        } else {
            addTeacher(addUserForm.getTeacher());
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateUser(EditUserForm editUserForm) {
        if (editUserForm.getIsStudent()) {
            updateStudent(editUserForm.getUserNo(), editUserForm.getStudent());
        } else {
            updateTeacher(editUserForm.getUserNo(), editUserForm.getTeacher());
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteUser(String userNo) {
        UserDto userDto = userMapper.findUserByUserNo(userNo);
        if (Objects.isNull(userDto)) {
            throw new UserExistException();
        }
        String updatedBy = UserThreadLocal.getUser().getUserNo();
        userDto.setUpdatedBy(updatedBy);
        userMapper.deleteUser(userDto);
        Integer userType = userDto.getUserType();
        if (userType.equals(UserTypeEnum.STUDENT.code)) {
            studentMapper.deleteStudent(userNo);
        }
        if (userType.equals(UserTypeEnum.TEACHER.code)) {
            teacherMapper.deleteTeacher(userNo);
        }
    }

    @Override
    public List<UserVo> getUsersByRoleId(String roleId) {
        return userMapper
                .findUsersByRoleId(roleId)
                .stream()
                .map(userDto -> {
                    UserVo userVo = new UserVo();
                    userVo.setUserNo(userDto.getUserNo());
                    userVo.setUsername(userDto.getUsername());
                    userVo.setUserType(Objects.requireNonNull(UserTypeEnum.findUserTypeEnumByCode(userDto.getUserType())).message);
                    userVo.setCreatedBy(userDto.getCreatedBy());
                    userVo.setCreatedDate(userDto.getCreatedDate());
                    userVo.setUpdatedBy(userDto.getUpdatedBy());
                    userVo.setUpdatedDate(userDto.getUpdatedDate());
                    return userVo;
        }).collect(Collectors.toList());
    }

    @Override
    @EventListener(classes = ExcelEvent.class)
    public void onListenExcel(ExcelEvent excelEvent) {
        String sqlName = excelEvent.getSqlName();
        String userNo = excelEvent.getUserNo();
        Map<String, Object> data = excelEvent.getData();
        UserDto userDto = null;
        switch (sqlName) {
            case Constant.STUDENT_TABLE:
                userDto = castDataToUserDto(data, userNo, true);
                break;
            case Constant.TEACHER_TABLE:
                userDto = castDataToUserDto(data, userNo, false);
                break;
            default:
                // 什么都不做
                break;
        }
        if (Objects.nonNull(userDto)) {
            if (excelEvent.getIsUpdate()) {
                userMapper.updateUser(userDto);
            } else {
                userMapper.addUser(userDto);
            }
        }
    }

    private UserDto castDataToUserDto(Map<String, Object> data, String userNo, boolean isStudent) {
        UserDto userDto = new UserDto();
        userDto.setUserNo(data.getOrDefault(isStudent ? "stuNo" : "empNo", "").toString());
        userDto.setUsername(data.getOrDefault(isStudent ? "stuName" : "empName", "").toString());
        userDto.setUserType(isStudent ? UserTypeEnum.STUDENT.code : UserTypeEnum.TEACHER.code);
        userDto.setPassword(initDefaultPassword());
        userDto.setCreatedBy(userNo);
        userDto.setUpdatedBy(userNo);
        return userDto;
    }

    private void addStudent(StudentVo student) {
        String userNo = student.getStuNo();
        StudentDto studentDto = studentMapper.findStudentByStuNo(userNo);
        if (Objects.nonNull(studentDto)) {
            throw new UserNoException();
        }
        UserDto userDto = userMapper.findUserByUserNo(userNo);
        if (Objects.nonNull(userDto)) {
            throw new UserNoException();
        }
        studentDto = JSON.parseObject(JSON.toJSONString(student), StudentDto.class);
        studentMapper.addStudent(studentDto);
        addUserDto(userNo, student.getStuName(), UserTypeEnum.STUDENT.code);
    }

    private void updateStudent(String stuNo, StudentVo student) {
        StudentDto studentDto = studentMapper.findStudentByStuNo(stuNo);
        if (Objects.isNull(studentDto)) {
            throw new UserNoException();
        }
        UserDto userDto = userMapper.findUserByUserNo(stuNo);
        if (Objects.isNull(userDto)) {
            throw new UserNoException();
        }
        studentDto = JSON.parseObject(JSON.toJSONString(student), StudentDto.class);
        studentDto.setStuNo(stuNo);
        userDto.setUsername(studentDto.getStuName());
        studentMapper.updateStudent(studentDto);
        userMapper.updateUsername(userDto);
    }

    private void addTeacher(TeacherVo teacher) {
        String userNo = teacher.getEmpNo();
        TeacherDto teacherDto = teacherMapper.findTeacherByEmpNo(userNo);
        if (Objects.nonNull(teacherDto)) {
            throw new UserNoException();
        }
        UserDto userDto = userMapper.findUserByUserNo(userNo);
        if (Objects.nonNull(userDto)) {
            throw new UserNoException();
        }
        teacherDto = JSON.parseObject(JSON.toJSONString(teacher), TeacherDto.class);
        teacherMapper.addTeacher(teacherDto);
        addUserDto(userNo, teacher.getEmpName(), UserTypeEnum.TEACHER.code);
    }

    private void updateTeacher(String empNo, TeacherVo teacher) {
        TeacherDto teacherDto = teacherMapper.findTeacherByEmpNo(empNo);
        if (Objects.isNull(teacherDto)) {
            throw new UserNoException();
        }
        UserDto userDto = userMapper.findUserByUserNo(empNo);
        if (Objects.isNull(userDto)) {
            throw new UserNoException();
        }
        teacherDto = JSON.parseObject(JSON.toJSONString(teacher), TeacherDto.class);
        teacherDto.setEmpNo(empNo);
        userDto.setUsername(teacherDto.getEmpName());
        teacherMapper.updateTeacher(teacherDto);
        userMapper.updateUsername(userDto);
    }

    private void addUserDto(String userNo, String username, int userType) {
        String createdBy = UserThreadLocal.getUser().getUserNo();
        UserDto userDto = new UserDto();
        userDto.setUserNo(userNo);
        userDto.setUsername(username);
        userDto.setUserType(userType);
        userDto.setPassword(initDefaultPassword());
        userDto.setCreatedBy(createdBy);
        userDto.setUpdatedBy(createdBy);
        userMapper.addUser(userDto);
    }

    private String initDefaultPassword() {
        return Md5Util.encrypt("111111");
    }

}
