package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.Limit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.event.UploadFinishEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 陈群矜
 */
@Order(5)
@Aspect
@Component
public class LimitAspect implements ApplicationContextAware {

    private final static Map<String, AtomicInteger> TOKEN_MAP = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.admin.admin_back.annotations.Limit)")
    public void limitAspect() {}

    @Around("limitAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (!method.isAnnotationPresent(Limit.class)) {
            // 不会走
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                return new Result<>(ResponseMessage.SYSTEM_ERROR);
            }
        }
        Limit limit = method.getAnnotation(Limit.class);
        boolean lazy = limit.lazy();
        String key = method.getDeclaringClass().getName() + "." + method.getName();
        AtomicInteger token = TOKEN_MAP.get(key);
        if (token.getAndDecrement() > 0) {
            Object result = null;
            try {
                result = joinPoint.proceed();
                if (!lazy) {
                    token.incrementAndGet();
                }
                return result;
            } catch (Throwable e) {
                result = new Result<>(ResponseMessage.SYSTEM_ERROR);
                token.incrementAndGet();
                return result;
            }
        } else {
            token.incrementAndGet();
            return new Result<>(ResponseMessage.LIMIT);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        handleLimitAnnotation(applicationContext.getBeansWithAnnotation(RestController.class));
        handleLimitAnnotation(applicationContext.getBeansWithAnnotation(Controller.class));
    }

    private void handleLimitAnnotation(Map<String, Object> beansMap) {
        for (Map.Entry<String, Object> beanEntry : beansMap.entrySet()) {
            // 采用cglib代理，生成的代理类是目标的子类，通过getSuperclass方法获取目标类型
            Class<?> clazz = beanEntry.getValue().getClass().getSuperclass();
            String className = clazz.getName();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Limit.class)) {
                    Limit annotation = method.getAnnotation(Limit.class);
                    int limit = annotation.value();
                    String name = method.getName();
                    String key = className + "." + name;
                    if (!TOKEN_MAP.containsKey(key)) {
                        TOKEN_MAP.put(key, new AtomicInteger(limit));
                    }
                }
            }
        }
    }

    @EventListener
    public void listen(UploadFinishEvent event) {
        String key = event.getKey();
        if (TOKEN_MAP.containsKey(key)) {
            TOKEN_MAP.get(key).incrementAndGet();
        }
    }

}
