package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Repository
public interface UserMapper {

    List<UserDto> findUserByUserType(Integer userType);

    List<String> findUserNoByUserType(Integer userType);

    UserDto findUserByUserNo(String userNo);

    Integer addUser(@Param("userDto") UserDto userDto);

    Integer updateUser(@Param("userDto") UserDto userDto);

    Integer deleteUser(@Param("userDto") UserDto userDto);

}
