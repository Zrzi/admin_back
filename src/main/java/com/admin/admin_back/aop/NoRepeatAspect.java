package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.annotations.NoRepeatSubmit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.exception.UserNoException;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈群矜
 */
@Aspect
@Component
public class NoRepeatAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.admin.admin_back.annotations.NoRepeatSubmit)")
    public void noRepeatAspect() {}

    @Around("noRepeatAspect()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            NoRepeatSubmit annotation = method.getAnnotation(NoRepeatSubmit.class);
            CheckRole checkRole = method.getAnnotation(CheckRole.class);
            int seconds = annotation.time();
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String path = request.getServletPath();
            // 注意，此处不一定有user，但一般post请求上都有@CheckRole注解，所以一定user不是null
            String userNo = Optional.ofNullable(UserThreadLocal.getUser()).orElseGet(UserDto::new).getUserNo();
            if (StringUtils.isBlank(userNo) && Objects.nonNull(checkRole)) {
                return new Result<>(ResponseMessage.NOT_LOGIN);
            }
            String sign = path + '/' + userNo;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(sign))) {
                return new Result<>(ResponseMessage.REPEAT_REQUEST);
            }
            redisTemplate.opsForValue().set(sign, "", seconds, TimeUnit.SECONDS);
            return pjp.proceed();
        } catch (Throwable throwable) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

}
