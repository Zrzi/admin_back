package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.Limit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * @author 陈群矜
 */
@Order(5)
@Aspect
@Component
public class LimitAspect implements ApplicationContextAware {

    private final static Map<String, Semaphore> SEMAPHORE_MAP = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.admin.admin_back.annotations.Limit)")
    public void limitAspect() {}

    @Around("limitAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getName();
        Semaphore semaphore = SEMAPHORE_MAP.get(methodName);
        boolean flag = semaphore.tryAcquire();
        if (flag) {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                return new Result<>(ResponseMessage.SYSTEM_ERROR);
            }
        } else {
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
            Class<?> clazz = beanEntry.getValue().getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Limit.class)) {
                    Limit annotation = method.getAnnotation(Limit.class);
                    int limit = annotation.value();
                    String name = method.getName();
                    if (!SEMAPHORE_MAP.containsKey(name)) {
                        SEMAPHORE_MAP.put(name, new Semaphore(limit));
                    }
                }
            }
        }
    }

}
