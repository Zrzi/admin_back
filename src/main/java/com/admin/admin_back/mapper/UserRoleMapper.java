package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.UserRoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Repository
public interface UserRoleMapper {

    List<String> findUnaddedUser(String roleId, String searchKey);

    List<UserRoleDto> findUserRoleByNo(String userNo);

    Integer findUserRoleCountByRoleId(@Param("roleId") String roleId,
                                      @Param("searchKey") String searchKey);

    List<UserRoleDto> findUserRolePageByRoleId(@Param("roleId") String roleId,
                                               @Param("start") Integer start,
                                               @Param("pageSize") Integer pageSize,
                                               @Param("searchKey") String searchKey);

    List<UserRoleDto> findUserRoleByRoleId(String roleId);

    UserRoleDto findUserRoleByUserNoAndRoleId(String userNo, String roleId);

    Integer addUserRole(@Param("userRole") UserRoleDto userRole);

    Integer updateUserRole(@Param("userRole") UserRoleDto userRole);

    Integer deleteUserRole(@Param("userRole") UserRoleDto userRole);

}
