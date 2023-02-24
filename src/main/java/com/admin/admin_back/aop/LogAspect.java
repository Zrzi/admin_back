package com.admin.admin_back.aop;

import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.service.LogTask;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author 陈群矜
 */
@Order(4)
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogTask logTask;

    @Pointcut("execution(public * com.admin.admin_back.controller.*.*(..))")
    public void logPoint() {}

    @Around("logPoint()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            UserDto user = UserThreadLocal.getUser();
            if (Objects.isNull(user)) {
                // 目前只有登录、退出、以及一些对外提供服务的接口会走这
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                String methodName = signature.getMethod().getName();
                Object[] args = pjp.getArgs();
                logTask.logBeforeMethod(null, methodName, args);
                Object result = pjp.proceed(args);
                logTask.logAfterMethod(null, methodName, result);
                return result;
            } else {
                String userNo = user.getUserNo();
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                String methodName = signature.getMethod().getName();
                Object[] args = pjp.getArgs();
                logTask.logBeforeMethod(userNo, methodName, args);
                Object result = pjp.proceed(args);
                logTask.logAfterMethod(userNo, methodName, result);
                return result;
            }
        } catch (Throwable e) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

}
