package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.RoleMapper;
import com.admin.admin_back.mapper.UserRoleMapper;
import com.admin.admin_back.pojo.dto.RoleDto;
import com.admin.admin_back.pojo.dto.UserRoleDto;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.UserRoleExistException;
import com.admin.admin_back.pojo.exception.UserRoleNotFoundException;
import com.admin.admin_back.pojo.form.AddMemberRoleForm;
import com.admin.admin_back.pojo.form.EditMemberRoleForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.service.MemberRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author 陈群矜
 */
@Service
public class MemberRoleServiceImpl implements MemberRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addMemberRole(AddMemberRoleForm addMemberRoleForm) {
        String roleId = addMemberRoleForm.getRoleId();
        RoleDto roleDto = roleMapper.findRoleByRoleId(roleId);
        if (Objects.isNull(roleDto)) {
            throw new RoleExistException();
        }
        String createdBy = UserThreadLocal.getUser().getUserNo();
        List<String> userNos = addMemberRoleForm.getUserNos();
        UserRoleDto userRoleDto = new UserRoleDto();
        for (String userNo : userNos) {
            userNo = userNo.trim();
            UserRoleDto temp = userRoleMapper.findUserRoleByUserNoAndRoleId(userNo, roleId);
            if (Objects.nonNull(temp)) {
                throw new UserRoleExistException();
            }
            userRoleDto.setUserNo(userNo);
            userRoleDto.setRoleId(roleId);
            // todo 待确定
            userRoleDto.setUserType(0);
            userRoleDto.setLevel(0);
            userRoleDto.setCreatedBy(createdBy);
            userRoleDto.setUpdatedBy(createdBy);
            userRoleMapper.addUserRole(userRoleDto);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateMemberRole(EditMemberRoleForm editMemberRoleForm) {
        String roleId = editMemberRoleForm.getRoleId();
        String userNo = editMemberRoleForm.getUesrNo();
        UserRoleDto userRoleDto = userRoleMapper.findUserRoleByUserNoAndRoleId(userNo, roleId);
        if (Objects.isNull(userRoleDto)) {
            throw new UserRoleNotFoundException();
        }
        String updatedBy = UserThreadLocal.getUser().getUserNo();
        userRoleDto.setLevel(editMemberRoleForm.getLevel());
        userRoleDto.setUpdatedBy(updatedBy);
        userRoleMapper.updateUserRole(userRoleDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteMemberRole(String userNo, String roleId) {
        UserRoleDto userRoleDto = userRoleMapper.findUserRoleByUserNoAndRoleId(userNo, roleId);
        if (Objects.isNull(userRoleDto)) {
            throw new UserRoleNotFoundException();
        }
        String updatedBy = UserThreadLocal.getUser().getUserNo();
        userRoleDto.setUpdatedBy(updatedBy);
        userRoleMapper.deleteUserRole(userRoleDto);
    }

}
