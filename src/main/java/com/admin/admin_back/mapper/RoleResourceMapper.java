package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.RoleResourceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
@Mapper
@Repository
public interface RoleResourceMapper {

    List<RoleResourceDto> findRoleResourceByRoleId(String roleId);

    List<RoleResourceDto> findRoleResourceByResourceId(String resourceId);

    RoleResourceDto findRoleResourceByRoleIdAndResourceId(String roleId, String resourceId);

    Integer addRoleResource(@Param("roleResource") RoleResourceDto roleResource);

    Integer deleteRoleResource(@Param("roleResource") RoleResourceDto roleResource);

}
