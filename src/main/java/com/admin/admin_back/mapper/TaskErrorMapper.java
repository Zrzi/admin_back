package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.TaskErrorDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Resource
public interface TaskErrorMapper {

    List<TaskErrorDto> findTaskErrorsByTaskId(@Param("taskId") String taskId);

    Integer insertTaskError(@Param("taskError") TaskErrorDto taskErrorDto);

}
