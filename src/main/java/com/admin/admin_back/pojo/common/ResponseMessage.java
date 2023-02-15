package com.admin.admin_back.pojo.common;

/**
 *
 * @author 陈群矜
 */
public enum ResponseMessage {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    /**
     * 系统不存在
     */
    SYSTEM_NOT_FOUNT(100000, "系统不存在"),

    /**
     * 系统名称不存在
     */
    SYSTEM_NAME_IS_NULL(100001, "系统名称不存在"),

    /**
     * 系统编码不存在
     */
    SYSTEM_ID_IS_NULL(100001, "系统编码不存在"),

    /**
     * 系统名称已经存在
     */
    SYSTEM_NAME_EXISTS(100002, "系统名称已经存在"),

    /**
     * 系统名称长度超过16个字符
     */
    SYSTEM_NAME_LENGTH_EXCESS(100003, "系统名称长度超过16个字符"),

    /**
     * 资源不存在
     */
    RESOURCE_NOT_FOUND(200000, "资源不存在"),

    /**
     * resource form检测不通过
     */
    RESOURCE_FORM_ERROR(200001, "表单验证出错"),

    /**
     * 资源名称已经存在
     */
    RESOURCE_NAME_EXISTS(200002, "资源名称已经存在"),

    /**
     * 资源不存在
     */
    PARENT_RESOURCE_NOT_FOUND(200003, "父资源不存在"),

    /**
     * 资源类型与父资源类型不一致
     */
    RESOURCE_TYPE_NOT_EQUAL(200004, "资源类型与父资源类型不一致"),

    /**
     * 存在子资源，不能删除
     */
    RESOURCE_CHILD_EXIST(200005, "存在子资源，不能删除"),

    /**
     * 角色不存在
     */
    ROLE_NOT_FOUND(300000, "角色不存在"),

    /**
     * role form检测不通过
     */
    ROLE_FORM_ERROR(300001, "表单验证出错"),

    /**
     * 角色名称已经存在
     */
    ROLE_NAME_EXIST(300002, "角色名称已经存在"),

    /**
     * 用户名缺失
     */
    USER_NO_NOT_FOUND(400000, "用户名缺失"),

    /**
     * 密码缺失
     */
    PASSWORD_NOT_FOUND(400001, "密码缺失"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(400002, "用户不存在"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR(400003, "密码错误"),

    /**
     * 参数异常
     */
    METHOD_ARGUMENT_NOT_VALID(800000, "参数异常"),

    /**
     * 参数错误
     */
    ILLEGAL_ARGUMENT(800001, "参数错误"),

    /**
     * 未登录
     */
    NOT_LOGIN(800002, "未登录"),

    /**
     * 无权限
     */
    NO_AUTHORITIES(800003, "无权限"),

    /**
     * 全局异常处理
     */
    SYSTEM_ERROR(900000, "系统内部异常"),
    ;

    /**
     * 状态码
     */
    public final int code;

    /**
     * 返回信息
     */
    public final String message;

    ResponseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
