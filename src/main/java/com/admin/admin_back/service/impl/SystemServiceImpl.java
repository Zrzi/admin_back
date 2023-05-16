package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.SystemMapper;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.SystemDto;
import com.admin.admin_back.pojo.enums.CodeTypeEnum;
import com.admin.admin_back.pojo.exception.BaseException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.exception.SystemNameExistException;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.SystemVo;
import com.admin.admin_back.service.DeleteCacheService;
import com.admin.admin_back.service.SystemService;
import com.admin.admin_back.utils.CacheTimeUtil;
import com.admin.admin_back.utils.GenerateCodeUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 陈群矜
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CacheTimeUtil cacheTimeUtil;

    @Autowired
    private DeleteCacheService deleteCacheService;

    @Override
    public List<SystemVo> getSystems() {
        List<SystemDto> systems = systemMapper.findSystemList();
        if (CollectionUtils.isEmpty(systems)) {
            throw new BaseException("系统数量为0");
        }
        return systems.stream().map(systemDto -> {
            SystemVo vo = new SystemVo();
            vo.setSystemId(systemDto.getSystemId());
            vo.setSystemName(systemDto.getSystemName());
            vo.setCreatedBy(systemDto.getCreatedBy());
            vo.setCreatedDate(systemDto.getCreatedDate());
            vo.setUpdatedBy(systemDto.getUpdatedBy());
            vo.setUpdatedDate(systemDto.getUpdatedDate());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public SystemVo getSystemBySystemId(String systemId) {
        String cacheValue = stringRedisTemplate.opsForValue().get("system:" + systemId);
        if (StringUtils.isNotBlank(cacheValue)) {
            return JSON.parseObject(cacheValue, SystemVo.class);
        }
        SystemDto systemDto = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(systemDto)) {
            throw new SystemExistException();
        }
        SystemVo vo = new SystemVo();
        vo.setSystemId(systemDto.getSystemId());
        vo.setSystemName(systemDto.getSystemName());
        vo.setCreatedBy(systemDto.getCreatedBy());
        vo.setCreatedDate(systemDto.getCreatedDate());
        vo.setUpdatedBy(systemDto.getUpdatedBy());
        vo.setUpdatedDate(systemDto.getUpdatedDate());
        stringRedisTemplate.opsForValue().set("system:" + systemId, vo.toString(), cacheTimeUtil.getCacheTime(), TimeUnit.SECONDS);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addSystem(String systemName) {
        if (systemMapper.findSystemBySystemName(systemName) != null) {
            throw new SystemNameExistException();
        }
        String systemId = GenerateCodeUtil.generateCode(CodeTypeEnum.SYSTEM);
        SystemDto systemDto = new SystemDto();
        systemDto.setSystemId(systemId);
        systemDto.setSystemName(systemName);
        String userNo = UserThreadLocal.getUser().getUserNo();
        systemDto.setCreatedBy(userNo);
        systemDto.setUpdatedBy(userNo);
        systemMapper.addSystem(systemDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateSystem(String systemId, String systemName) {
        SystemDto systemDto = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(systemDto)) {
            throw new SystemExistException();
        }
        if (!StringUtils.equals(systemName, systemDto.getSystemName())) {
            if (Objects.nonNull(systemMapper.findSystemBySystemName(systemName))) {
                throw new SystemNameExistException();
            }
        }
        systemDto.setSystemId(systemId);
        systemDto.setSystemName(systemName);
        String userNo = UserThreadLocal.getUser().getUserNo();
        systemDto.setUpdatedBy(userNo);
        systemMapper.updateSystemBySystemId(systemDto);
        String key = "system:" + systemId;
        deleteCacheService.deleteRedisCache(key);
        deleteCacheService.deleteRedisCache(key, Constant.INT_2);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteSystem(String systemId) {
        SystemDto system = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(system)) {
            throw new SystemExistException();
        }
        String userNo = UserThreadLocal.getUser().getUserNo();
        system.setUpdatedBy(userNo);
        systemMapper.deleteSystemBySystemId(system);
        String key = "system:" + systemId;
        deleteCacheService.deleteRedisCache(key);
        deleteCacheService.deleteRedisCache(key, Constant.INT_2);
    }

}
