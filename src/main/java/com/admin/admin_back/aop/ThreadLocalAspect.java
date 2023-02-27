package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.RefreshToken;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.dto.UserDto;
import com.admin.admin_back.pojo.exception.BaseException;
import com.admin.admin_back.pojo.exception.OtherLoginException;
import com.admin.admin_back.pojo.threadlocals.TokenThreadLocal;
import com.admin.admin_back.pojo.threadlocals.UserThreadLocal;
import com.admin.admin_back.utils.JwtTokenUtil;
import com.admin.admin_back.utils.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 陈群矜
 */
@Order(2)
@Aspect
@Component
public class ThreadLocalAspect {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
                    RefreshToken annotation = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(RefreshToken.class);
                    if (Objects.isNull(annotation)) {
                        // 如果不存在该注解，说明目标方法不是refreshToken，拦截
                        return new Result<>(ResponseMessage.TOKEN_EXPIRED);
                    }
                } else {
                    setThreadLocal(token);
                }
                return pjp.proceed();
            }
        } catch (OtherLoginException exception) {
            return new Result<>(ResponseMessage.OTHER_LOGIN);
        } catch (Throwable e) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        } finally {
            clearThreadLocal();
        }
    }

    private void setThreadLocal(String token) {
        String userNo = jwtTokenUtil.getUserNoFromToken(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        // 检查是否已经在其它设备上登录
        String md5Digest = Optional.ofNullable(redisTemplate.opsForValue().get(userNo)).orElse("").toString();
        if (StringUtils.isNotBlank(md5Digest)) {
            String digest = Md5Util.digest(token);
            if (!StringUtils.equals(digest, md5Digest)) {
                throw new OtherLoginException();
            }
        }
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
