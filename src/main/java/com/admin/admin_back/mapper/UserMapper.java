package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 陈群矜
 */
@Mapper
@Repository
public interface UserMapper {

    UserDto findUserByUserNo(String userNo);

    Integer updateUser(UserDto userDto);

    Integer deleteUser(UserDto userDto);

}
