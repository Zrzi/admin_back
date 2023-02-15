package com.admin.admin_back.pojo.enums;

/**
 *
 * @author 陈群矜
 */
public enum ResourceTypeEnum {

    /**
     * 功能操作类型
     */
    operation(0, "功能操作"),

    /**
     * 菜单类型
     */
    menu(1, "菜单"),

    /**
     * 页面元素类型
     */
    element(2, "页面元素"),

    /**
     * 文件资源类型
     */
    file(3, "文件资源"),
    ;

    /**
     * 编码 存储在数据库中
     */
    public final int code;

    /**
     * 信息 返回给前端
     */
    public final String message;

    ResourceTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResourceTypeEnum findResourceTypeEnumByCode(int code) {
        for (ResourceTypeEnum resourceTypeEnum : ResourceTypeEnum.values()) {
            if (code == resourceTypeEnum.code) {
                return resourceTypeEnum;
            }
        }
        // 默认是功能操作
        return ResourceTypeEnum.operation;
    }

    public static ResourceTypeEnum findResourceTypeEnumByMessage(String message) {
        for (ResourceTypeEnum resourceTypeEnum : ResourceTypeEnum.values()) {
            if (message.equals(resourceTypeEnum.message)) {
                return resourceTypeEnum;
            }
        }
        return null;
    }

}
