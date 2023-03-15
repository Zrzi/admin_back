package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.AddMemberRoleForm;
import com.admin.admin_back.pojo.form.EditMemberRoleForm;
import com.admin.admin_back.pojo.vo.UserVo;

import java.util.List;

/**
 * @author 陈群矜
 */
public interface MemberRoleService {

    List<UserVo> getUnaddedUserByRoleId(String roleId, String searchKey);

    Integer getMemberRolesCount(String roleId, String searchKey);

    List<UserVo> getMemberRolePageByRoleId(String roleId, Integer start, Integer pageSize, String searchKey);

    List<UserVo> getMemberRoleByRoleId(String roleId);

    void addMemberRole(AddMemberRoleForm addMemberRoleForm);

    void updateMemberRole(EditMemberRoleForm editMemberRoleForm);

    void deleteMemberRole(String userNo, String roleId);

}
