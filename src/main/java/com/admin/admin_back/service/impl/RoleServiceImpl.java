package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.RoleMapper;
import com.admin.admin_back.mapper.SystemMapper;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.RoleDto;
import com.admin.admin_back.pojo.dto.SystemDto;
import com.admin.admin_back.pojo.enums.CodeTypeEnum;
import com.admin.admin_back.pojo.exception.RoleExistException;
import com.admin.admin_back.pojo.exception.RoleNameExistException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.form.RoleForm;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.RoleVo;
import com.admin.admin_back.pojo.vo.SystemRoleVo;
import com.admin.admin_back.service.DeleteCacheService;
import com.admin.admin_back.service.RoleService;
import com.admin.admin_back.utils.CacheTimeUtil;
import com.admin.admin_back.utils.GenerateCodeUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author 陈群矜
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CacheTimeUtil cacheTimeUtil;

    @Autowired
    private DeleteCacheService deleteCacheService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RoleVo getRoleByRoleId(String roleId) {
        String cacheValue = stringRedisTemplate.opsForValue().get("role:" + roleId);
        if (StringUtils.isNotBlank(cacheValue)) {
            return JSON.parseObject(cacheValue, RoleVo.class);
        }
        RoleDto roleDto = roleMapper.findRoleByRoleId(roleId);
        if (Objects.isNull(roleDto)) {
            throw new RoleExistException();
        }
        RoleVo roleVo = new RoleVo();
        roleVo.setRoleId(roleDto.getRoleId());
        roleVo.setRoleName(roleDto.getRoleName());
        roleVo.setSystemId(roleDto.getSystemId());
        roleVo.setSystemName(roleDto.getSystemName());
        roleVo.setCreatedBy(roleDto.getCreatedBy());
        roleVo.setCreatedDate(roleDto.getCreatedDate());
        roleVo.setUpdatedBy(roleDto.getUpdatedBy());
        roleVo.setUpdatedDate(roleDto.getUpdatedDate());
        stringRedisTemplate.opsForValue().set("role:" + roleId, roleVo.toString(), cacheTimeUtil.getCacheTime(), TimeUnit.SECONDS);
        return roleVo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<SystemRoleVo> getRole() {
        List<SystemDto> systemDtos = systemMapper.findSystemList();
        if (CollectionUtils.isEmpty(systemDtos)) {
            return null;
        }
        List<SystemRoleVo> vos = new ArrayList<>();
        for (SystemDto systemDto : systemDtos) {
            SystemRoleVo vo = new SystemRoleVo();
            vo.setSystemId(systemDto.getSystemId());
            vo.setSystemName(systemDto.getSystemName());
            List<RoleDto> roleDtos = roleMapper.findRoleBySystemId(systemDto.getSystemId());
            if (CollectionUtils.isEmpty(roleDtos)) {
                vo.setRoles(null);
            } else {
                List<RoleVo> roleVos = roleDtos.stream().map(roleDto -> {
                    RoleVo roleVo = new RoleVo();
                    roleVo.setRoleId(roleDto.getRoleId());
                    roleVo.setRoleName(roleDto.getRoleName());
                    roleVo.setSystemId(roleDto.getSystemId());
                    roleVo.setSystemName(roleDto.getSystemName());
                    roleVo.setCreatedBy(roleDto.getCreatedBy());
                    roleVo.setCreatedDate(roleDto.getCreatedDate());
                    roleVo.setUpdatedBy(roleDto.getUpdatedBy());
                    roleVo.setUpdatedDate(roleDto.getUpdatedDate());
                    return roleVo;
                }).collect(Collectors.toList());
                vo.setRoles(roleVos);
            }
            vos.add(vo);
        }
        return vos;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addRole(RoleForm roleForm) {
        String systemId = roleForm.getSystemId();
        SystemDto systemDto = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(systemDto)) {
            throw new SystemExistException();
        }
        String roleName = roleForm.getRoleName();
        if (Objects.nonNull(roleMapper.findRoleByRoleNameAndSystemId(roleName, systemId))) {
            throw new RoleNameExistException();
        }
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(GenerateCodeUtil.generateCode(CodeTypeEnum.ROLE));
        roleDto.setRoleName(roleName);
        roleDto.setSystemId(systemId);
        roleDto.setSystemName(systemDto.getSystemName());
        String userNo = UserThreadLocal.getUser().getUserNo();
        roleDto.setCreatedBy(userNo);
        roleDto.setUpdatedBy(userNo);
        roleMapper.addRole(roleDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateRole(RoleForm roleForm) {
        String roleId = roleForm.getRoleId();
        RoleDto roleDto = roleMapper.findRoleByRoleId(roleId);
        if (Objects.isNull(roleDto)) {
            throw new RoleExistException();
        }
        String systemId = roleDto.getSystemId();
        String roleName = roleForm.getRoleName();
        if (!StringUtils.equals(roleName, roleDto.getRoleName())) {
            if (Objects.nonNull(roleMapper.findRoleByRoleNameAndSystemId(roleName, systemId))) {
                throw new RoleNameExistException();
            }
        }
        roleDto.setRoleName(roleName);
        String userNo = UserThreadLocal.getUser().getUserNo();
        roleDto.setUpdatedBy(userNo);
        String key = "role:" + roleId;
        deleteCacheService.deleteRedisCache(key);
        roleMapper.updateRoleByRoleId(roleDto);
        deleteCacheService.deleteRedisCache(key, Constant.INT_5);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteRoleByRoleId(String roleId) {
        RoleDto roleDto = roleMapper.findRoleByRoleId(roleId);
        if (Objects.isNull(roleDto)) {
            throw new RoleExistException();
        }
        String userNo = UserThreadLocal.getUser().getUserNo();
        roleDto.setUpdatedBy(userNo);
        String key = "role:" + roleId;
        deleteCacheService.deleteRedisCache(key);
        roleMapper.deleteRoleByRoleId(roleDto);
        deleteCacheService.deleteRedisCache(key, Constant.INT_5);
    }

}
