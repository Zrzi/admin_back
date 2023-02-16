package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.RoleForm;
import com.admin.admin_back.pojo.vo.RoleVo;
import com.admin.admin_back.pojo.vo.SystemRoleVo;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
public interface RoleService {

    RoleVo getRoleByRoleId(String roleId);

    List<SystemRoleVo> getRole();

    void addRole(RoleForm roleForm);

    void updateRole(RoleForm roleForm);

    void deleteRoleByRoleId(String roleId);

}
