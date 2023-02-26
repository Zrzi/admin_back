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

    @Pointcut("execution(public * com.admin.admin_back.controller.*.*(..))")
    public void threadLocalAspect() {}

    @Around("threadLocalAspect()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
            String token = request.getHeader("Authorization");
            if (StringUtils.isBlank(token)) {
                // 部分接口不需要提供token，这里放行，如果需要检验在之后的Aspect中检查
                return pjp.proceed();
            } else {
                if (!jwtTokenUtil.validateToken(token)) {
                    // 携带的token过期
                    return new Result<>(ResponseMessage.NOT_LOGIN);
                }
                setThreadLocal(token);
                return pjp.proceed();
            }
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
