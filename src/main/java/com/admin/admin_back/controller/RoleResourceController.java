package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.annotations.LogAnnotation;
import com.admin.admin_back.annotations.NoRepeatSubmit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.form.EditRoleResourceForm;
import com.admin.admin_back.pojo.vo.RoleResourceVo;
import com.admin.admin_back.service.RoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 陈群矜
 */
@Api(tags = "管理角色资源关联接口")
@RestController
public class RoleResourceController {

    @Autowired
    private RoleResourceService roleResourceService;

    @ApiOperation("根据系统编码与角色编码获取角色资源关联信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "systemId", value = "系统编码", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色编码", required = true)
    })
    @LogAnnotation
    @CheckRole("getRoleResources")
    @GetMapping("/roleResource/get")
    public Result<?> getRoleResources(@RequestParam("systemId") String systemId,
                                      @RequestParam("roleId") String roleId) {
        systemId = systemId.trim();
        roleId = roleId.trim();
        if (StringUtils.isBlank(systemId)) {
            return new Result<>(ResponseMessage.SYSTEM_ID_IS_NULL);
        }
        if (StringUtils.isBlank(roleId)) {
            return new Result<>(ResponseMessage.ROLE_ID_IS_NULL);
        }
        try {
            List<RoleResourceVo> roleResources = roleResourceService.getRoleResources(systemId, roleId);
            return new Result<>(ResponseMessage.SUCCESS, roleResources);
        } catch (SystemExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NOT_FOUNT);
        } catch (RoleExistException roleExistException) {
            return new Result<>(ResponseMessage.ROLE_NOT_FOUND);
        }
    }

    @ApiOperation("编辑角色资源关联")
    @LogAnnotation
    @NoRepeatSubmit
    @CheckRole("editRoleResource")
    @PostMapping("/roleResource/post")
    public Result<?> editRoleResource(@RequestBody EditRoleResourceForm editRoleResourceForm) {
        String flag = checkEditRoleResourceForm(editRoleResourceForm);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.EDIT_ROLE_RESOURCE_FORM_ERROR, null, flag);
        }
        try {
            roleResourceService.editRoleResources(editRoleResourceForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (SystemExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NOT_FOUNT);
        } catch (RoleExistException exception) {
            return new Result<>(ResponseMessage.ROLE_NOT_FOUND);
        }
    }

    private String checkEditRoleResourceForm(EditRoleResourceForm editRoleResourceForm) {
        String systemId = editRoleResourceForm.getSystemId();
        if (StringUtils.isBlank(systemId)) {
            return "系统编码为空";
        }
        editRoleResourceForm.setSystemId(systemId.trim());
        String roleId = editRoleResourceForm.getRoleId();
        if (StringUtils.isBlank(roleId)) {
            return "角色编码为空";
        }
        editRoleResourceForm.setRoleId(roleId.trim());
        List<String> resourceIds = editRoleResourceForm.getResourceIds();
        if (CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }
        for (String resourceId : resourceIds) {
            if (StringUtils.isBlank(resourceId)) {
                return "资源编码为空";
            }
        }
        return "";
    }

}
