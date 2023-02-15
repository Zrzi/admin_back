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

    List<UserRoleDto> findUserRoleByNo(String userNo);

}
