package com.admin.admin_back.aop;

import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.utils.JwtTokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Aspect
@Component
public class LogAspect {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("execution(public * com.admin.admin_back.controller.*.*(..))")
    public void logPoint() {}

    @Around("logPoint()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            UserDto user = UserThreadLocal.getUser();
            String username = user.getUsername();
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            String methodName = signature.getMethod().getName();
            Object[] args = pjp.getArgs();
            // todo 打印日志
            Object result = pjp.proceed(args);
            // todo 打印result
            return result;
        } catch (Throwable e) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

}
