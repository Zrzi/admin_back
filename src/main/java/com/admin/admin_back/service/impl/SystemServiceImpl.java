package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.SystemMapper;
import com.admin.admin_back.pojo.dto.SystemDto;
import com.admin.admin_back.pojo.enums.CodeTypeEnum;
import com.admin.admin_back.pojo.exception.BaseException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.exception.SystemNameExistException;
import com.admin.admin_back.pojo.vo.SystemVo;
import com.admin.admin_back.service.SystemService;
import com.admin.admin_back.utils.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;

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
    public void addSystem(String systemName) {
        if (systemMapper.findSystemBySystemName(systemName) != null) {
            throw new SystemNameExistException();
        }
        String systemId = GenerateCodeUtil.generateCode(CodeTypeEnum.SYSTEM);
        SystemDto systemDto = new SystemDto();
        systemDto.setSystemId(systemId);
        systemDto.setSystemName(systemName);
        // todo 获取用户名
        systemDto.setCreatedBy("");
        systemDto.setUpdatedBy("");
        systemMapper.addSystem(systemDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateSystem(String systemId, String systemName) {
        if (Objects.nonNull(systemMapper.findSystemBySystemName(systemName))) {
            throw new SystemNameExistException();
        }
        SystemDto systemDto = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(systemDto)) {
            throw new SystemExistException();
        }
        systemDto.setSystemId(systemId);
        systemDto.setSystemName(systemName);
        // todo 获取用户名
        systemDto.setUpdatedBy("");
        systemMapper.updateSystemBySystemId(systemDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteSystem(String systemId) {
        SystemDto system = systemMapper.findSystemBySystemId(systemId);
        if (Objects.isNull(system)) {
            throw new SystemExistException();
        }
        // todo 获取用户名
        system.setUpdatedBy("");
        systemMapper.deleteSystemBySystemId(system);
    }

}
