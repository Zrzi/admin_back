package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.UserMapper;
import com.admin.admin_back.mapper.UserRoleMapper;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.dto.UserRoleDto;
import com.admin.admin_back.pojo.enums.UserTypeEnum;
import com.admin.admin_back.pojo.exception.PasswordException;
import com.admin.admin_back.pojo.exception.UserExistException;
import com.admin.admin_back.pojo.vo.UserRoleVo;
import com.admin.admin_back.service.UserService;
import com.admin.admin_back.utils.JwtTokenUtil;
import com.admin.admin_back.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 陈群矜
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String login(String userNo, String password) {
        UserDto user = userMapper.findUserByUserNo(userNo);
        if (Objects.isNull(user)) {
            throw new UserExistException();
        }
        boolean checkPassword = Md5Util.verify(password, user.getPassword());
        if (!checkPassword) {
            throw new PasswordException();
        }
        List<UserRoleDto> dtos = userRoleMapper.findUserRoleByNo(userNo);
        if (CollectionUtils.isEmpty(dtos)) {
            return jwtTokenUtil.generateToken(user, new ArrayList<>());
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
            return jwtTokenUtil.generateToken(user, userRoleVos);
        }
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

}
