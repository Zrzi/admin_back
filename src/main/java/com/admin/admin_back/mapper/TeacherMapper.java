package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.TeacherDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Repository
public interface TeacherMapper {

    List<TeacherDto> findTeachers();

    TeacherDto findTeacherByEmpNo(String empNo);

    Integer addTeacher(@Param("teacher") TeacherDto teacher);

}
