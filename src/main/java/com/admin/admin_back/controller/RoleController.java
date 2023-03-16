package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.annotations.Limit;
import com.admin.admin_back.annotations.LogAnnotation;
import com.admin.admin_back.annotations.NoRepeatSubmit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.RoleNameExistException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.form.DeleteRoleForm;
import com.admin.admin_back.pojo.form.RoleForm;
import com.admin.admin_back.pojo.vo.SystemRoleVo;
import com.admin.admin_back.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
@Api(tags = "角色相关接口")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("获取角色列表")
    @Limit(1000)
    @LogAnnotation
    @CheckRole("getRole")
    @GetMapping("/role/get")
    public Result<List<SystemRoleVo>> getRole() {
        return new Result<>(ResponseMessage.SUCCESS, roleService.getRole());
    }

    @ApiOperation("根据角色编码获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色编码", required = true)
    })
    @Limit(1000)
    @LogAnnotation
    @CheckRole("getRoleByRoleId")
    @GetMapping("/role/getByRoleId")
    public Result<?> getRoleByRoleId(@RequestParam("roleId") String roleId) {
        try {
            return new Result<>(ResponseMessage.SUCCESS, roleService.getRoleByRoleId(roleId));
        } catch (RoleExistException exception) {
            return new Result<>(ResponseMessage.ROLE_NOT_FOUND);
        }
    }

    @ApiOperation("添加角色")
    @Limit(1000)
    @LogAnnotation
    @NoRepeatSubmit
    @CheckRole("addRole")
    @PostMapping("/role/post")
    public Result<?> addRole(@RequestBody RoleForm roleForm) {
        String flag = checkValidRoleForm(roleForm, false);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.ROLE_FORM_ERROR, null, flag);
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

    @ApiOperation("添加角色")
    @Limit(1000)
    @LogAnnotation
    @NoRepeatSubmit
    @CheckRole("updateRole")
    @PostMapping("/role/update")
    public Result<?> updateRole(@RequestBody RoleForm roleForm) {
        String flag = checkValidRoleForm(roleForm, true);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.ROLE_FORM_ERROR, null, flag);
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

    @ApiOperation("删除角色")
    @Limit(1000)
    @LogAnnotation
    @NoRepeatSubmit
    @CheckRole("removeRole")
    @PostMapping("/role/delete")
    public Result<?> removeRole(@RequestBody DeleteRoleForm deleteRoleForm) {
        String roleId = deleteRoleForm.getRoleId();
        if (StringUtils.isBlank(roleId)) {
            return new Result<>(ResponseMessage.ROLE_FORM_ERROR, "请输入角色编码");
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
            String roleId = roleForm.getRoleId();
            if (StringUtils.isBlank(roleId)) {
                return "角色编码为空";
            }
            roleForm.setRoleId(roleId.trim());
        } else {
            String systemId = roleForm.getSystemId();
            if (StringUtils.isBlank(systemId)) {
                return "系统编码为空";
            }
            roleForm.setSystemId(systemId.trim());
        }
        String roleName = roleForm.getRoleName();
        if (StringUtils.isBlank(roleName)) {
            return "角色名称为空";
        }
        roleName = roleName.trim();
        if (roleName.length() > Constant.INT_16) {
            return "角色名称超过16个字符";
        }
        roleForm.setRoleName(roleName);
        return "";
    }

}
