package com.admin.admin_back.controller;

import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.RoleNameExistException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.form.RoleForm;
import com.admin.admin_back.pojo.vo.RoleVo;
import com.admin.admin_back.pojo.vo.SystemRoleVo;
import com.admin.admin_back.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role/get")
    public Result<List<SystemRoleVo>> getRole() {
        return new Result<>(ResponseMessage.SUCCESS, roleService.getRole());
    }

    @PostMapping("/role/post")
    public Result<?> addRole(RoleForm roleForm) {
        String flag = checkValidRoleForm(roleForm, false);
        if (!StringUtils.isEmpty(flag)) {
            return new Result<>(ResponseMessage.ROLE_FORM_ERROR, flag);
        }
        try {
            roleService.addRole(roleForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (SystemExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NOT_FOUNT);
        } catch (RoleNameExistException exception) {
            return new Result<>(ResponseMessage.ROLE_NAME_EXIST);
        }
    }

    @PostMapping("/role/post")
    public Result<?> updateRole(RoleForm roleForm) {
        String flag = checkValidRoleForm(roleForm, true);
        if (!StringUtils.isEmpty(flag)) {
            return new Result<>(ResponseMessage.ROLE_FORM_ERROR, flag);
        }
        try {
            roleService.updateRole(roleForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (RoleExistException exception) {
            return new Result<>(ResponseMessage.ROLE_NOT_FOUND);
        } catch (RoleNameExistException exception) {
            return new Result<>(ResponseMessage.ROLE_NAME_EXIST);
        }
    }

    @PostMapping("/role/post")
    public Result<?> removeRole(String roleId) {
        roleId = roleId.trim();
        if (StringUtils.isEmpty(roleId)) {
            return new Result<>(ResponseMessage.ROLE_FORM_ERROR, "角色编码不存在");
        }
        try {
            roleService.deleteRoleByRoleId(roleId);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (RoleExistException exception) {
            return new Result<>(ResponseMessage.ROLE_NOT_FOUND);
        }
    }

    private String checkValidRoleForm(RoleForm roleForm, boolean isUpdate) {
        if (isUpdate) {
            String roleId = roleForm.getRoleId().trim();
            if (StringUtils.isEmpty(roleId)) {
                return "角色编码为空";
            }
            roleForm.setRoleId(roleId);
        } else {
            String systemId = roleForm.getSystemId().trim();
            if (StringUtils.isEmpty(systemId)) {
                return "系统编码为空";
            }
            roleForm.setSystemId(systemId);
        }
        String roleName = roleForm.getRoleName().trim();
        if (StringUtils.isEmpty(roleName)) {
            return "角色名称为空";
        }
        if (roleName.length() > 16) {
            return "角色名称超过16个字符";
        }
        roleForm.setRoleName(roleName);
        return "";
    }

}
