package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.ResourceForm;
import com.admin.admin_back.pojo.vo.ResourceVo;

import java.util.List;

public interface ResourceService {

    Integer getResourcesCount(String systemId);

    List<ResourceVo> getResourcesPageBySystemId(String systemId, Integer start, Integer end);

    List<ResourceVo> getResourcesBySystemId(String systemId);

    void addResource(ResourceForm resourceForm);

    void updateResource(ResourceForm resourceForm);

    void deleteResourceByResourceId(String resourceId);

}
