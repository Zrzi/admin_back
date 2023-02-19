package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.ResourceForm;
import com.admin.admin_back.pojo.vo.ResourceVo;

import java.util.List;

/**
 * @author 陈群矜
 */
public interface ResourceService {

    Integer getResourcesCount(String systemId);

    Boolean checkAuthority(String resourceId);

    List<ResourceVo> getResourcesPageBySystemId(String systemId, Integer start, Integer end);

    List<ResourceVo> getResourcesBySystemId(String systemId);

    ResourceVo getResourceById(String resourceId);

    void addResource(ResourceForm resourceForm);

    void updateResource(ResourceForm resourceForm);

    void deleteResourceByResourceId(String resourceId);

}
