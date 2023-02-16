package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.BaseException;
import com.admin.admin_back.pojo.exception.SystemExistException;
import com.admin.admin_back.pojo.exception.SystemNameExistException;
import com.admin.admin_back.pojo.form.SystemForm;
import com.admin.admin_back.pojo.vo.SystemVo;
import com.admin.admin_back.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author 陈群矜
 */
@RestController
public class SystemController {

    @Autowired
    private SystemService systemService;

    @CheckRole("getSystems")
    @GetMapping(value = "/system/get")
    public Result<List<SystemVo>> getSystems() {
        try {
            List<SystemVo> systems = systemService.getSystems();
            return new Result<>(ResponseMessage.SUCCESS, systems);
        } catch (BaseException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NOT_FOUNT);
        }
    }

    @CheckRole("addSystem")
    @PostMapping(value = "/system/post")
    public Result<?> addSystem(SystemForm form, @RequestHeader("Authorization") String jwt) {
        String systemName = form.getSystemName().trim();
        if (StringUtils.isEmpty(systemName)) {
            return new Result<>(ResponseMessage.SYSTEM_NAME_IS_NULL);
        }
        if (systemName.length() > 16) {
            return new Result<>(ResponseMessage.SYSTEM_NAME_LENGTH_EXCESS);
        }
        try {
            systemService.addSystem(systemName);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (SystemNameExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NAME_EXISTS);
        }
    }

    @CheckRole("updateSystem")
    @PostMapping(value = "/system/update")
    public Result<?> updateSystem(SystemForm form) {
        String systemId = form.getSystemId().trim();
        String systemName = form.getSystemName().trim();
        if (StringUtils.isEmpty(systemId)) {
            return new Result<>(ResponseMessage.SYSTEM_ID_IS_NULL);
        }
        if (StringUtils.isEmpty(systemName)) {
            return new Result<>(ResponseMessage.SYSTEM_NAME_IS_NULL);
        }
        if (systemName.length() > 16) {
            return new Result<>(ResponseMessage.SYSTEM_NAME_LENGTH_EXCESS);
        }
        try {
            systemService.updateSystem(systemId, systemName);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (SystemNameExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NAME_EXISTS);
        } catch (SystemExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NOT_FOUNT);
        }
    }

    @CheckRole("deleteSystem")
    @PostMapping("/system/delete")
    public Result<?> deleteSystem(String systemId) {
        if (StringUtils.isEmpty(systemId)) {
            return new Result<>(ResponseMessage.SYSTEM_ID_IS_NULL);
        }
        try {
            systemService.deleteSystem(systemId);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (SystemExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NOT_FOUNT);
        }
    }

}
