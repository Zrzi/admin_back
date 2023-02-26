package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.annotations.LogAnnotation;
import com.admin.admin_back.annotations.NoRepeatSubmit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.UserRoleExistException;
import com.admin.admin_back.pojo.exception.UserRoleNotFoundException;
import com.admin.admin_back.pojo.form.AddMemberRoleForm;
import com.admin.admin_back.pojo.form.DeleteMemberRoleForm;
import com.admin.admin_back.pojo.form.EditMemberRoleForm;
import com.admin.admin_back.pojo.vo.UserVo;
import com.admin.admin_back.service.MemberRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈群矜
 */
@Api(tags = "用户角色关联相关接口")
@RestController
public class MemberRoleController {

    @Autowired
    private MemberRoleService memberRoleService;

    @ApiOperation("根据角色编码获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色编码", required = true),
            @ApiImplicitParam(name = "start", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true)
    })
    @LogAnnotation
    @CheckRole("getMemberRole")
    @GetMapping("/memberRole/get")
    public Result<?> getMemberRole(@RequestParam("roleId") String roleId,
                                   @RequestParam(value = "start", required = false) Integer start,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (start == null || start < 0) {
            start = 0;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }
        List<UserVo> memberRoles = memberRoleService.getMemberRolePageByRoleId(roleId, start, pageSize);
        Integer count = memberRoleService.getMemberRolesCount(roleId);
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("users", memberRoles);
            put("count", count);
        }};
        return new Result<>(ResponseMessage.SUCCESS, map);
    }

    @ApiOperation("根据角色编码获取不具有该角色的用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色编码", required = true)
    })
    @LogAnnotation
    @CheckRole("getUnaddedUser")
    @GetMapping("/memberRole/getUnaddedUser")
    public Result<?> getUnaddedUser(@RequestParam("roleId") String roleId) {
        return new Result<>(ResponseMessage.SUCCESS, memberRoleService.getUnaddedUserByRoleId(roleId));
    }

    @ApiOperation("添加用户角色关联")
    @LogAnnotation
    @NoRepeatSubmit
    @CheckRole("addMemberRole")
    @PostMapping("/memberRole/addMemberRole")
    public Result<?> addMemberRole(@RequestBody AddMemberRoleForm addMemberRoleForm) {
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

//    @CheckRole("updateMemberRole")
//    @PostMapping("/memberRole/updateMemberRole")
//    public Result<?> updateMemberRole(EditMemberRoleForm editMemberRoleForm) {
//        String flag = checkEditMemberRoleForm(editMemberRoleForm);
//        if (StringUtils.isNotBlank(flag)) {
//            return new Result<>(ResponseMessage.MEMBER_FORM_ERROR, flag);
//        }
//        try {
//            memberRoleService.updateMemberRole(editMemberRoleForm);
//            return new Result<>(ResponseMessage.SUCCESS);
//        } catch (UserRoleNotFoundException exception) {
//            return new Result<>(ResponseMessage.USER_ROLE_NOT_FOUND);
//        }
//    }

    @ApiOperation("删除用户角色关联")
    @LogAnnotation
    @NoRepeatSubmit
    @CheckRole("deleteMemberRole")
    @PostMapping("/memberRole/deleteMemberRole")
    public Result<?> deleteMemberRole(@RequestBody DeleteMemberRoleForm deleteMemberRoleForm) {
        String roleId = deleteMemberRoleForm.getRoleId();
        String userNo = deleteMemberRoleForm.getUserNo();
        if (StringUtils.isBlank(roleId)) {
            return new Result<>(ResponseMessage.ROLE_ID_IS_NULL);
        }
        if (StringUtils.isBlank(userNo)) {
            return new Result<>(ResponseMessage.USER_NO_NOT_FOUND);
        }
        try {
            memberRoleService.deleteMemberRole(userNo, roleId);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (UserRoleNotFoundException exception) {
            return new Result<>(ResponseMessage.USER_ROLE_NOT_FOUND);
        }
    }

    private String checkAddMemberRoleForm(AddMemberRoleForm addMemberRoleForm) {
        String roleId = addMemberRoleForm.getRoleId();
        if (StringUtils.isBlank(roleId)) {
            return "角色编码为空";
        }
        addMemberRoleForm.setRoleId(roleId.trim());
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
        String roleId = editMemberRoleForm.getRoleId();
        if (StringUtils.isBlank(roleId)) {
            return "角色编码为空";
        }
        editMemberRoleForm.setRoleId(roleId.trim());
        Integer level = editMemberRoleForm.getLevel();
        if (level < 0 || level > 10) {
            return "角色等级应该在0到10之间";
        }
        return "";
    }

}
