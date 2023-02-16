package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.UserRoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Repository
public interface UserRoleMapper {

    List<String> findUnaddedUser(String roleId);

    List<UserRoleDto> findUserRoleByNo(String userNo);

    List<UserRoleDto> findUserRoleByRoleId(String roleId);

    UserRoleDto findUserRoleByUserNoAndRoleId(String userNo, String roleId);

    Integer addUserRole(UserRoleDto userRole);

    Integer updateUserRole(UserRoleDto userRole);

    Integer deleteUserRole(UserRoleDto userRole);

}
