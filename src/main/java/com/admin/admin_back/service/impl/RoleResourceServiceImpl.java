package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.ResourceMapper;
import com.admin.admin_back.mapper.RoleMapper;
import com.admin.admin_back.mapper.RoleResourceMapper;
import com.admin.admin_back.mapper.SystemMapper;
import com.admin.admin_back.pojo.dto.ResourceDto;
import com.admin.admin_back.pojo.dto.RoleDto;
import com.admin.admin_back.pojo.dto.RoleResourceDto;
import com.admin.admin_back.pojo.dto.SystemDto;
import com.admin.admin_back.pojo.enums.ResourceTypeEnum;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.form.EditRoleResourceForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.RoleResourceVo;
import com.admin.admin_back.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 陈群矜
 */
@Service
public class RoleResourceServiceImpl implements RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<RoleResourceVo> getRoleResources(String systemId, String roleId) {
        SystemDto system = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(system)) {
            throw new SystemExistException();
        }
        RoleDto role = roleMapper.findRoleByRoleId(roleId);
        if (Objects.isNull(role)) {
            throw new RoleExistException();
        }
        List<ResourceDto> resourceDtos = resourceMapper.findResourceBySystemId(systemId);
        List<RoleResourceVo> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(resourceDtos)) {
            return result;
        }
        for (ResourceDto resourceDto : resourceDtos) {
            RoleResourceVo roleResourceVo = new RoleResourceVo();
            roleResourceVo.setRoleId(roleId);
            roleResourceVo.setRoleName(roleResourceVo.getRoleName());
            roleResourceVo.setSystemId(systemId);
            roleResourceVo.setSystemName(system.getSystemName());
            roleResourceVo.setResourceId(resourceDto.getResourceId());
            roleResourceVo.setResourceName(resourceDto.getResourceName());
            roleResourceVo.setResourceUrl(resourceDto.getResourceUrl());
            roleResourceVo.setResourceType(ResourceTypeEnum.findResourceTypeEnumByCode(resourceDto.getResourceType()).message);
            RoleResourceDto roleResourceDto = roleResourceMapper.findRoleResourceByRoleIdAndResourceId(roleId, resourceDto.getResourceId());
            if (Objects.nonNull(roleResourceDto)) {
                roleResourceVo.setHas(true);
                roleResourceVo.setCreatedBy(resourceDto.getCreatedBy());
                roleResourceVo.setCreatedDate(resourceDto.getCreatedDate());
                roleResourceVo.setUpdatedBy(resourceDto.getUpdatedBy());
                roleResourceVo.setUpdatedDate(resourceDto.getUpdatedDate());
            }
            result.add(roleResourceVo);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editRoleResources(EditRoleResourceForm editRoleResourceForm) {
        String systemId = editRoleResourceForm.getSystemId();
        String roleId = editRoleResourceForm.getRoleId();
        SystemDto system = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(system)) {
            throw new SystemExistException();
        }
        RoleDto role = roleMapper.findRoleByRoleId(roleId);
        if (Objects.isNull(role)) {
            throw new RoleExistException();
        }
        // 遍历roleResourceDtos，如果resourceId存在，说明是修改，不做改变；如果resourceId不存在，说明是新增；剩下的是删除
        Set<String> resourceIds = editRoleResourceForm.getResourceIds().stream().map(String::trim).collect(Collectors.toSet());
        List<RoleResourceDto> roleResourceDtos = roleResourceMapper.findRoleResourceByRoleId(roleId);
        List<RoleResourceDto> toBeInserted = new ArrayList<>();
        List<RoleResourceDto> toBeDeleted = new ArrayList<>();
        String userNo = UserThreadLocal.getUser().getUserNo();
        for (RoleResourceDto roleResourceDto : roleResourceDtos) {
            String resourceId = roleResourceDto.getResourceId();
            if (resourceIds.contains(resourceId)) {
                // 原本已经有的，不做修改
                resourceIds.remove(resourceId);
            } else {
                // 原本有的，但现在不需要了
                RoleResourceDto temp = new RoleResourceDto();
                temp.setRoleId(roleId);
                temp.setResourceId(resourceId);
                temp.setUpdatedBy(userNo);
                toBeDeleted.add(temp);
            }
        }
        // 剩余的id是待添加的
        for (String id : resourceIds) {
            RoleResourceDto temp = new RoleResourceDto();
            temp.setRoleId(roleId);
            temp.setResourceId(id);
            temp.setCreatedBy(userNo);
            temp.setUpdatedBy(userNo);
            toBeInserted.add(temp);
        }
        insertRoleResource(toBeInserted);
        deleteRoleResource(toBeDeleted);
    }

    private void insertRoleResource(List<RoleResourceDto> toBeInserted) {
        if (CollectionUtils.isEmpty(toBeInserted)) {
            return;
        }
        for (RoleResourceDto roleResourceDto : toBeInserted) {
            roleResourceMapper.addRoleResource(roleResourceDto);
        }
    }

    private void deleteRoleResource(List<RoleResourceDto> toBeDeleted) {
        if (CollectionUtils.isEmpty(toBeDeleted)) {
            return;
        }
        for (RoleResourceDto roleResourceDto : toBeDeleted) {
            roleResourceMapper.deleteRoleResource(roleResourceDto);
        }
    }

}
