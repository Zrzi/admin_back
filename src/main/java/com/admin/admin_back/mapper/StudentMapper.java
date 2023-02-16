package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.StudentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Repository
public interface StudentMapper {

    List<StudentDto> findStudents();

    StudentDto findStudentByStuNo(String stuNo);

    Integer addStudent(@Param("student") StudentDto student);

}
