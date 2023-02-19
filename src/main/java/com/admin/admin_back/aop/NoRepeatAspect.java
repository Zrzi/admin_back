package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.NoRepeatSubmit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.exception.UserNoException;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
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
        Object result = null;
        try {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            NoRepeatSubmit annotation = methodSignature.getMethod().getAnnotation(NoRepeatSubmit.class);
            int seconds = annotation.time();
            HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String path = request.getServletPath();
            // 注意，此处不一定有user，但一般post请求上都有@CheckRole注解，所以一定user不是null
            String userNo = Optional
                    .ofNullable(UserThreadLocal.getUser())
                    .orElseThrow(RuntimeException::new)
                    .getUserNo();
            String sign = path + '/' + userNo;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(sign))) {
                return new Result<>(ResponseMessage.REPEAT_REQUEST);
            }
            redisTemplate.opsForValue().set(sign, sign, seconds, TimeUnit.SECONDS);
            return pjp.proceed();
        } catch (RuntimeException exception) {
            return new Result<>(ResponseMessage.NOT_LOGIN);
        } catch (Throwable throwable) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

}
