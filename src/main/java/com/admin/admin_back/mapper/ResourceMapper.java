package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.ResourceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
@Mapper
@Repository
public interface ResourceMapper {

    Integer findResourceCount(@Param("systemId") String systemId);

    List<ResourceDto> findResourceByUserNo(String userNo);

    List<ResourceDto> findMenuBySystemId(@Param("systemId") String systemId, @Param("userNo") String userNo);

    List<ResourceDto> findResourceByUserNoAndResourceType(@Param("userNo") String userNo, @Param("resourceType") Integer resourceType);

    List<ResourceDto> findResourcePageBySystemId(@Param("systemId") String systemId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<ResourceDto> findResourceBySystemId(String systemId);

    // List<String> findResourceByParentResource(String parentResource);

    ResourceDto findResourceByResourceId(String resourceId);

    ResourceDto findResourceByResourceName(String resourceName);

    ResourceDto findResourceByResourceNameAndSystemId(String resourceName, String systemId);

    Integer addResource(@Param("resource") ResourceDto resource);

    Integer updateResourceByResourceId(@Param("resource") ResourceDto resource);

    Integer deleteResourceByResourceId(@Param("resource") ResourceDto resource);

}
