package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.NoRepeatSubmit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈群矜
 */
@Order(5)
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
            // 注意，此处不一定有user，所以userDto可能是null
            UserDto user = UserThreadLocal.getUser();
            if (Objects.isNull(user)) {
                // 新增LimitAspect，对于外部系统调用，无法提供userNo，防重复意义不大
                // 比如，A用户提交数据a，B用户提交数据b，A用户再提交数据a
                // A用户两次提交间隔时间较短，但redis缓存已经被B提交的数据覆盖，因此，判断是不重复
                // 所以，对于这类请求，不校验重复
                return pjp.proceed();
            }
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            NoRepeatSubmit annotation = method.getAnnotation(NoRepeatSubmit.class);
            String methodName = method.getName();
            int seconds = annotation.time();
            List<Object> args = new ArrayList<>();
            for (Object arg : pjp.getArgs()) {
                if (arg instanceof MultipartFile) {
                    // 如果是文件，跳过
                    continue;
                }
                args.add(arg);
            }
            String sign = methodName + '/' + user.getUserNo();
            String value = JSON.toJSONString(args);
            String cachedValue = (String) redisTemplate.opsForValue().get(sign);
            if (StringUtils.equals(value, cachedValue)) {
                return new Result<>(ResponseMessage.REPEAT_REQUEST);
            }
            redisTemplate.opsForValue().set(sign, value, seconds, TimeUnit.SECONDS);
            return pjp.proceed();
        } catch (Throwable throwable) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

}
