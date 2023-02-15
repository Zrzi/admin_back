package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.RoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
@Mapper
@Repository
public interface RoleMapper {

    List<RoleDto> findRoleBySystemId(String systemId);

    RoleDto findRoleByRoleId(String roleId);

    RoleDto findRoleByRoleNameAndSystemId(String roleName, String systemId);

    Integer addRole(RoleDto role);

    Integer updateRoleByRoleId(RoleDto role);

    Integer deleteRoleByRoleId(RoleDto role);

}
