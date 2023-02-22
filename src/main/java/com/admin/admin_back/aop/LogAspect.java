package com.admin.admin_back.aop;

import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

    private final static Logger LOGGER = LogManager.getLogger(LogAspect.class);

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
                LOGGER.info("执行方法{}，参数是{}", methodName, JSON.toJSONString(args));
                return pjp.proceed(args);
            } else {
                String userNo = user.getUserNo();
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                String methodName = signature.getMethod().getName();
                Object[] args = pjp.getArgs();
                LOGGER.info("用户名：{}，执行方法：{}，参数：{}", userNo, methodName, JSON.toJSONString(args));
                Object result = pjp.proceed(args);
                LOGGER.info("用户名：{}，执行方法：{}，返回结果：{}", userNo, methodName, JSON.toJSONString(result));
                return result;
            }
        } catch (Throwable e) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

}
