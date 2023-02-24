package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.LogAnnotation;
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

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author 陈群矜
 */
@Order(4)
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogTask logTask;

    @Pointcut("@annotation(com.admin.admin_back.annotations.LogAnnotation)")
    public void logPoint() {}

    @Around("logPoint()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            String userNo = Optional.ofNullable(UserThreadLocal.getUser()).orElseGet(UserDto::new).getUserNo();
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();
            LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
            String methodName = method.getName();
            Object[] args = pjp.getArgs();
            logTask.logBeforeMethod(userNo, methodName, annotation.inEnabled() ? args : null);
            Object result = pjp.proceed(args);
            logTask.logAfterMethod(userNo, methodName, annotation.outEnabled() ? result : null);
            return result;
        } catch (Throwable e) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

}
