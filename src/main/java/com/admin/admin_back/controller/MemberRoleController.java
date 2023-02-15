package com.admin.admin_back.controller;

import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.UserRoleExistException;
import com.admin.admin_back.pojo.exception.UserRoleNotFoundException;
import com.admin.admin_back.pojo.form.AddMemberRoleForm;
import com.admin.admin_back.pojo.form.EditMemberRoleForm;
import com.admin.admin_back.service.MemberRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 陈群矜
 */
@RestController
public class MemberRoleController {

    @Autowired
    private MemberRoleService memberRoleService;

    @GetMapping("/memberRole/get")
    public Result<?> getMemberRole(String roleId) {
        return null;
    }

    @GetMapping("/memberRole/getUnadded")
    public Result<?> getMemberRole(String roleId, String systemId) {
        return null;
    }

    @PostMapping("/memberRole/addMemberRole")
    public Result<?> addMemberRole(AddMemberRoleForm addMemberRoleForm) {
        String flag = checkAddMemberRoleForm(addMemberRoleForm);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.MEMBER_FORM_ERROR, flag);
        }
        try {
            memberRoleService.addMemberRole(addMemberRoleForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (RoleExistException exception) {
            return new Result<>(ResponseMessage.ROLE_NOT_FOUND);
        } catch (UserRoleExistException exception) {
            return new Result<>(ResponseMessage.USER_ROLE_EXIST);
        }

    }

    @PostMapping("/memberRole/updateMemberRole")
    public Result<?> updateMemberRole(EditMemberRoleForm editMemberRoleForm) {
        String flag = checkEditMemberRoleForm(editMemberRoleForm);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.MEMBER_FORM_ERROR, flag);
        }
        try {
            memberRoleService.updateMemberRole(editMemberRoleForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (UserRoleNotFoundException exception) {
            return new Result<>(ResponseMessage.USER_ROLE_NOT_FOUND);
        }
    }

    @PostMapping("/memberRole/deleteMemberRole")
    public Result<?> deleteMemberRole(String roleId, String userNo) {
        if (StringUtils.isBlank(roleId)) {
            return new Result<>(ResponseMessage.ROLE_ID_IS_NULL);
        }
        if (StringUtils.isBlank(userNo)) {
            return new Result<>(ResponseMessage.USER_NO_NOT_FOUND);
        }
        try {
            memberRoleService.deleteMemberRole(userNo.trim(), roleId.trim());
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (UserRoleNotFoundException exception) {
            return new Result<>(ResponseMessage.USER_ROLE_NOT_FOUND);
        }
    }

    private String checkAddMemberRoleForm(AddMemberRoleForm addMemberRoleForm) {
        String roleId = addMemberRoleForm.getRoleId().trim();
        if (StringUtils.isBlank(roleId)) {
            return "角色编码为空";
        }
        addMemberRoleForm.setRoleId(roleId);
        List<String> userNos = addMemberRoleForm.getUserNos();
        if (CollectionUtils.isEmpty(userNos)) {
            return "待添加的用户编码列表为空";
        }
        for (String userNo : userNos) {
            if (StringUtils.isBlank(userNo)) {
                return "用户编码为空";
            }
        }
        return "";
    }

    private String checkEditMemberRoleForm(EditMemberRoleForm editMemberRoleForm) {
        String userNo = editMemberRoleForm.getUesrNo().trim();
        if (StringUtils.isBlank(userNo)) {
            return "用户名为空";
        }
        editMemberRoleForm.setUesrNo(userNo);
        String roleId = editMemberRoleForm.getRoleId().trim();
        if (StringUtils.isBlank(roleId)) {
            return "角色编码为空";
        }
        editMemberRoleForm.setRoleId(roleId);
        Integer level = editMemberRoleForm.getLevel();
        if (level < 0 || level > 10) {
            return "角色等级应该在0到10之间";
        }
        return "";
    }

}
