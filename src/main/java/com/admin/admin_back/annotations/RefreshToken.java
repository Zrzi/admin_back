package com.admin.admin_back.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在ThreadLocalAspect中，如果携带token，会检验token的过期时间并获取UserDto信息
 * 但是，如果是刷新token，则token一定过期
 * 在方法上添加该注解，用于放行
 * @author 陈群矜
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RefreshToken {
}
