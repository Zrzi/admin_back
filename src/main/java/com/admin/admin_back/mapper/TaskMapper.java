package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.TaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Resource
public interface TaskMapper {

    List<TaskDto> findTaskByUserNo(@Param("userNo") String userNo,
                                   @Param("start") int start,
                                   @Param("pageSize") int pageSize);

    Integer findTotalCountByUserNo(@Param("userNo") String userNo);

    TaskDto findTaskByTaskId(@Param("taskId") String taskId);

    Integer insertTask(@Param("taskDto") TaskDto taskDto);

    Integer updateTask(@Param("taskDto") TaskDto taskDto);

}
