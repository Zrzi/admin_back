package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.ResourceMapper;
import com.admin.admin_back.mapper.SystemMapper;
import com.admin.admin_back.pojo.dto.ResourceDto;
import com.admin.admin_back.pojo.dto.SystemDto;
import com.admin.admin_back.pojo.enums.CodeTypeEnum;
import com.admin.admin_back.pojo.enums.ResourceTypeEnum;
import com.admin.admin_back.pojo.exception.*;
import com.admin.admin_back.pojo.form.ResourceForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.ResourceVo;
import com.admin.admin_back.service.ResourceService;
import com.admin.admin_back.utils.GenerateCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Override
    public Integer getResourcesCount(String systemId) {
        return resourceMapper.findResourceCount(systemId);
    }

    @Override
    public List<ResourceVo> getResourcesPageBySystemId(String systemId, Integer start, Integer pageSize) {
        List<ResourceDto> resourceBySystemId = resourceMapper.findResourcePageBySystemId(systemId, start, pageSize);
        if (CollectionUtils.isEmpty(resourceBySystemId)) {
            throw new BaseException("资源不存在");
        }
        return resourceBySystemId.stream().map(resourceDto -> {
            ResourceVo vo = new ResourceVo();
            vo.setResourceId(resourceDto.getResourceId());
            vo.setResourceName(resourceDto.getResourceName());
            vo.setSystemId(resourceDto.getSystemId());
            vo.setSystemName(resourceDto.getSystemName());
            vo.setResourceUrl(resourceDto.getResourceUrl());
            vo.setResourceType(ResourceTypeEnum.findResourceTypeEnumByCode(resourceDto.getResourceType()).message);
//            vo.setParentResource(resourceDto.getParentResource());
            vo.setCreatedBy(resourceDto.getCreatedBy());
            vo.setCreatedDate(resourceDto.getCreatedDate());
            vo.setUpdatedBy(resourceDto.getUpdatedBy());
            vo.setUpdatedDate(resourceDto.getUpdatedDate());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ResourceVo> getResourcesBySystemId(String systemId) {
        List<ResourceDto> resourceBySystemId = resourceMapper.findResourceBySystemId(systemId);
        if (CollectionUtils.isEmpty(resourceBySystemId)) {
            throw new BaseException("资源不存在");
        }
        return resourceBySystemId.stream().map(resourceDto -> {
            ResourceVo vo = new ResourceVo();
            vo.setResourceId(resourceDto.getResourceId());
            vo.setResourceName(resourceDto.getResourceName());
            vo.setSystemId(resourceDto.getSystemId());
            vo.setSystemName(resourceDto.getSystemName());
            vo.setResourceUrl(resourceDto.getResourceUrl());
            vo.setResourceType(ResourceTypeEnum.findResourceTypeEnumByCode(resourceDto.getResourceType()).message);
//            vo.setParentResource(resourceDto.getParentResource());
            vo.setCreatedBy(resourceDto.getCreatedBy());
            vo.setCreatedDate(resourceDto.getCreatedDate());
            vo.setUpdatedBy(resourceDto.getUpdatedBy());
            vo.setUpdatedDate(resourceDto.getUpdatedDate());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public ResourceVo getResourceById(String resourceId) {
        ResourceDto resourceDto = resourceMapper.findResourceByResourceId(resourceId);
        if (Objects.isNull(resourceDto)) {
            throw new ResourceExistException();
        }
        ResourceVo vo = new ResourceVo();
        vo.setResourceId(resourceDto.getResourceId());
        vo.setResourceName(resourceDto.getResourceName());
        vo.setSystemId(resourceDto.getSystemId());
        vo.setSystemName(resourceDto.getSystemName());
        vo.setResourceUrl(resourceDto.getResourceUrl());
        vo.setResourceType(ResourceTypeEnum.findResourceTypeEnumByCode(resourceDto.getResourceType()).message);
//        vo.setParentResource(resourceDto.getParentResource());
        vo.setCreatedBy(resourceDto.getCreatedBy());
        vo.setCreatedDate(resourceDto.getCreatedDate());
        vo.setUpdatedBy(resourceDto.getUpdatedBy());
        vo.setUpdatedDate(resourceDto.getUpdatedDate());
        return vo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addResource(ResourceForm resourceForm) {
        String systemId = resourceForm.getSystemId();
        SystemDto system = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(system)) {
            throw new SystemExistException();
        }
        String resourceName = resourceForm.getResourceName();
        if (Objects.nonNull(resourceMapper.findResourceByResourceName(resourceName))) {
            throw new ResourceNameExistException();
        }
//        String parentResource = resourceForm.getParentResource().trim();
//        ResourceDto parent = null;
//        if (StringUtils.isNotBlank(parentResource)) {
//            parent = resourceMapper.findResourceByResourceId(parentResource);
//            if (Objects.isNull(parent)) {
//                throw new ResourceParentExistException();
//            }
//        }
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setResourceId(GenerateCodeUtil.generateCode(CodeTypeEnum.RESOURCE));
        resourceDto.setResourceName(resourceName);
        resourceDto.setSystemId(systemId);
        resourceDto.setSystemName(system.getSystemName());
        resourceDto.setResourceUrl(resourceForm.getResourceUrl());
        String type = resourceForm.getResourceType();
        int code = Objects.requireNonNull(ResourceTypeEnum.findResourceTypeEnumByMessage(type)).code;
//        if (Objects.nonNull(parent) && code != parent.getResourceType()) {
//            throw new ResourceTypeException();
//        }
        resourceDto.setResourceType(code);
//        resourceDto.setParentResource(resourceDto.getParentResource());
        String userNo = UserThreadLocal.getUser().getUserNo();
        resourceDto.setCreatedBy(userNo);
        resourceDto.setUpdatedBy(userNo);
        resourceMapper.addResource(resourceDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateResource(ResourceForm resourceForm) {
        String resourceId = resourceForm.getResourceId();
        ResourceDto resourceDto = resourceMapper.findResourceByResourceId(resourceId);
        if (Objects.isNull(resourceDto)) {
            throw new ResourceExistException();
        }
        String systemId = resourceDto.getSystemId();
        String resourceName = resourceForm.getResourceName();
        if (!StringUtils.equals(resourceName, resourceDto.getResourceName())) {
            if (Objects.nonNull(resourceMapper.findResourceByResourceNameAndSystemId(resourceName, systemId))) {
                throw new ResourceNameExistException();
            }
        }
//        String parentResource = resourceForm.getParentResource();
//        ResourceDto parent = null;
//        if (StringUtils.isNotEmpty(parentResource)) {
//            parent = resourceMapper.findResourceByResourceId(parentResource);
//            if (Objects.isNull(parent)) {
//                throw new ResourceParentExistException();
//            }
//        }
        resourceDto.setResourceName(resourceName);
        resourceDto.setResourceUrl(resourceForm.getResourceUrl());
        String type = resourceForm.getResourceType();
        int code = Objects.requireNonNull(ResourceTypeEnum.findResourceTypeEnumByMessage(type)).code;
//        if (Objects.nonNull(parent) && code != parent.getResourceType()) {
//            throw new ResourceTypeException();
//        }
        resourceDto.setResourceType(code);
//        resourceDto.setParentResource(resourceDto.getParentResource());
        String userNo = UserThreadLocal.getUser().getUserNo();
        resourceDto.setUpdatedBy(userNo);
        resourceMapper.updateResourceByResourceId(resourceDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteResourceByResourceId(String resourceId) {
        ResourceDto resourceDto = resourceMapper.findResourceByResourceId(resourceId);
        if (Objects.isNull(resourceDto)) {
            throw new ResourceExistException();
        }
//        List<String> ids = resourceMapper.findResourceByParentResource(resourceId);
//        if (!CollectionUtils.isEmpty(ids)) {
//            throw new ResourceChildExistException();
//        }
        String userNo = UserThreadLocal.getUser().getUserNo();
        resourceDto.setUpdatedBy(userNo);
        resourceMapper.deleteResourceByResourceId(resourceDto);
    }

}
