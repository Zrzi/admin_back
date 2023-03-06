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
     * 资源编码缺失
     */
    RESOURCE_ID_NOT_FOUND(200005, "资源编码缺失"),

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
     * 角色编码不存在
     */
    ROLE_ID_IS_NULL(300003, "角色编码不存在"),

    /**
     * 编辑角色资源表单错误
     */
    EDIT_ROLE_RESOURCE_FORM_ERROR(300004, "编辑角色资源表单错误"),

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
     * 表单错误
     */
    MEMBER_FORM_ERROR(400004, "表单错误"),

    /**
     * 用户角色对应关系已经存在
     */
    USER_ROLE_EXIST(400005, "用户角色对应关系已经存在"),

    /**
     * 用户角色对应关系不存在
     */
    USER_ROLE_NOT_FOUND(400006, "用户角色对应关系不存在"),

    /**
     * 用户类型错误
     */
    USER_TYOE_ERROR(400007, "用户类型错误"),

    /**
     * 数据为空
     */
    CONTENT_NOT_FOUND(500000, "数据为空"),

    /**
     * 密钥为空
     */
    KEY_NOT_FOUND(500001, "密钥为空"),

    /**
     * Excel映射配置编码不存在
     */
    EXCEL_ID_IS_NULL(600000, "Excel映射配置编码不存在"),

    /**
     * Excel映射配置不存在
     */
    EXCEL_NOT_FOUND(600001, "Excel映射配置不存在"),

    /**
     * Excel表单错误
     */
    EXCEL_FORM_ERROR(600002, "Excel表单错误"),

    /**
     * Excel名称已经存在
     */
    EXCEL_NAME_EXIST(600003, "Excel名称已经存在"),

    /**
     * 文件类型错误
     */
    FILE_TYPE_ERROR(600004, "文件类型错误"),

    /**
     * 不存在对应的Excel映射
     */
    EXCEL_NAME_NOT_FOUND(600005, "不存在对应的Excel映射"),

    /**
     * Excel某一列不存在对应的配置
     */
    EXCEL_COLUMN_NAME_ERROR(600006, "Excel某一列不存在对应的配置"),

    /**
     * Excel表格中存在数据错误
     */
    EXCEL_DATA_ERROR(600007, "Excel表格中存在数据错误"),

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
     * 重复请求
     */
    REPEAT_REQUEST(800004, "重复请求"),

    /**
     * 密钥缺失
     */
    AES_KEY_NOT_FOUND(800005, "密钥缺失"),

    /**
     * 已经在其它设备登录
     */
    OTHER_LOGIN(800006, "已经在其它设备登录"),

    /**
     * token过期
     */
    TOKEN_EXPIRED(800007, "token过期"),

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
