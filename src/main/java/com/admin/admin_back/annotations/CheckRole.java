package com.admin.admin_back.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解在方法上，用于检查角色切面
 * @author 陈群矜
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRole {

    /**
     * 接口对应的资源名称
     */
    public String value() default "";

}
