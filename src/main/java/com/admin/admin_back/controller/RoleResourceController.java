package com.admin.admin_back.controller;

import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.form.EditRoleResourceForm;
import com.admin.admin_back.pojo.vo.RoleResourceVo;
import com.admin.admin_back.service.RoleResourceService;
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
public class RoleResourceController {

    @Autowired
    private RoleResourceService roleResourceService;

    @GetMapping("/roleResource/get")
    public Result<?> getRoleResources(String systemId, String roleId) {
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

    @PostMapping("/roleResource/post")
    public Result<?> editRoleResource(EditRoleResourceForm editRoleResourceForm) {
        String flag = checkEditRoleResourceForm(editRoleResourceForm);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.EDIT_ROLE_RESOURCE_FORM_ERROR, flag);
        }
        return null;
    }

    private String checkEditRoleResourceForm(EditRoleResourceForm editRoleResourceForm) {
        String systemId = editRoleResourceForm.getSystemId().trim();
        if (StringUtils.isBlank(systemId)) {
            return "系统编码为空";
        }
        editRoleResourceForm.setSystemId(systemId);
        String roleId = editRoleResourceForm.getRoleId().trim();
        if (StringUtils.isBlank(roleId)) {
            return "角色编码为空";
        }
        editRoleResourceForm.setRoleId(roleId);
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
