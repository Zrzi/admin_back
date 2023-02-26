package com.admin.admin_back.annotations;

import java.lang.annotation.*;

/**
 * 控制日志打印
 * 用户信息，比如密码等日志不输出
 * @author 陈群矜
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /**
     * 输入日志禁用
     * @return boolean类型
     */
    boolean inEnabled() default true;

    /**
     * 输出日志禁用
     * @return boolean类型
     */
    boolean outEnabled() default true;

}
