package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.SystemDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
@Mapper
@Repository
public interface SystemMapper {

    List<SystemDto> findSystemList();

    SystemDto findSystemBySystemId(String systemId);

    SystemDto findSystemBySystemName(String systemName);

    Integer addSystem(SystemDto system);

    Integer updateSystemBySystemId(SystemDto system);

    Integer deleteSystemBySystemId(SystemDto system);

}
