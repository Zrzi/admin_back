package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.EditRoleResourceForm;
import com.admin.admin_back.pojo.vo.RoleResourceVo;

import java.util.List;

public interface RoleResourceService {

    List<RoleResourceVo> getRoleResources(String systemId, String roleId, String searchKey);

    void editRoleResources(EditRoleResourceForm editRoleResourceForm);

}
