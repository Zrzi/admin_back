package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.RoleResourceDto;
import org.apache.ibatis.annotations.Mapper;
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

    Integer addRoleResource(RoleResourceDto roleResource);

    Integer deleteRoleResource(RoleResourceDto roleResource);

}
