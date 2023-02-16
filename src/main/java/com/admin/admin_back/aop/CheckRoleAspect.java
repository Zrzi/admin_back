package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.mapper.ResourceMapper;
import com.admin.admin_back.mapper.RoleResourceMapper;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.dto.ResourceDto;
import com.admin.admin_back.pojo.dto.RoleResourceDto;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.threadlocals.TokenThreadLocal;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.pojo.vo.UserRoleVo;
import com.admin.admin_back.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;

@Order(2)
@Aspect
@Component
public class CheckRoleAspect {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("@annotation(com.admin.admin_back.annotations.CheckRole)")
    public void checkRole() {}

    @Around("checkRole()")
    public Object around(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            String token = TokenThreadLocal.getToken();
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            CheckRole annotation = methodSignature.getMethod().getAnnotation(CheckRole.class);
            String resourceName = annotation.value();
            if (!checkAuthority(token, resourceName)) {
                return new Result<>(ResponseMessage.NO_AUTHORITIES);
            }
            result = pjp.proceed();
            return result;
        } catch (Throwable throwable) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        } finally {
            UserThreadLocal.removeUser();
        }
    }

    private boolean checkAuthority(String token, String resourceName) {
        List<LinkedHashMap<String, Object>> userRoleVos = (List<LinkedHashMap<String, Object>>) jwtTokenUtil.getUserRoleFromToken(token);
        if (CollectionUtils.isEmpty(userRoleVos)) {
            return false;
        }
        for (LinkedHashMap<String, Object> map : userRoleVos) {
            String roleId = map.getOrDefault("roleId", "").toString();
            List<RoleResourceDto> roleResources = roleResourceMapper.findRoleResourceByRoleId(roleId);
            for (RoleResourceDto roleResourceDto : roleResources) {
                String resourceId = roleResourceDto.getResourceId();
                ResourceDto resource = resourceMapper.findResourceByResourceId(resourceId);
                if (StringUtils.equals(resourceName, resource.getResourceName())) {
                    return true;
                }
            }
        }
        return false;
    }

}
