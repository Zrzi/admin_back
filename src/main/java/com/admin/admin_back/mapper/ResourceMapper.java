package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.ResourceDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
@Mapper
@Repository
public interface ResourceMapper {

    List<ResourceDto> findResourceBySystemId(String systemId);

    List<String> findResourceByParentResource(String parentResource);

    ResourceDto findResourceByResourceId(String resourceId);

    ResourceDto findResourceByResourceName(String resourceName);

    ResourceDto findResourceByResourceNameAndSystemId(String resourceName, String systemId);

    Integer addResource(ResourceDto resource);

    Integer updateResourceByResourceId(ResourceDto resource);

    Integer deleteResourceByResourceId(ResourceDto resource);

}
