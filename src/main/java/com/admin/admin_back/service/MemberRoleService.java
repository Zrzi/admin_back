package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.AddMemberRoleForm;
import com.admin.admin_back.pojo.form.EditMemberRoleForm;

/**
 * @author 陈群矜
 */
public interface MemberRoleService {

    void addMemberRole(AddMemberRoleForm addMemberRoleForm);

    void updateMemberRole(EditMemberRoleForm editMemberRoleForm);

    void deleteMemberRole(String userNo, String roleId);

}
