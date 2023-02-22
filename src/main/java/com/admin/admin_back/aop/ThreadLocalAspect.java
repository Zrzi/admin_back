package com.admin.admin_back.aop;

import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.threadlocals.TokenThreadLocal;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 陈群矜
 */
@Order(2)
@Aspect
@Component
public class ThreadLocalAspect {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("@annotation(com.admin.admin_back.annotations.CheckRole)")
    public void aspectPoint() {}

    @Around("aspectPoint()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
            String token = request.getHeader("Authorization");
            if (StringUtils.isBlank(token) || !jwtTokenUtil.validateToken(token)) {
                return new Result<>(ResponseMessage.NOT_LOGIN);
            }
            setThreadLocal(token);
            return pjp.proceed();
        } catch (Throwable e) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        } finally {
            clearThreadLocal();
        }
    }

    private void setThreadLocal(String token) {
        String userNo = jwtTokenUtil.getUserNoFromToken(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDto user = new UserDto();
        user.setUserNo(userNo);
        user.setUsername(username);
        UserThreadLocal.setUser(user);
        TokenThreadLocal.setTokenThreadLocal(token);
    }

    private void clearThreadLocal() {
        UserThreadLocal.removeUser();
        TokenThreadLocal.removeToken();
    }

}
