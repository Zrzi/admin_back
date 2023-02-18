package com.admin.admin_back.pojo.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 陈群矜
 */
public enum UserTypeEnum {

    /**
     * 系统用户
     */
    ADMIN_USER(0, "系统用户"),

    /**
     * 学生
     */
    STUDENT(1, "学生"),

    /**
     * 教师
     */
    TEACHER(2, "教师"),

    ;
    public int code;

    public String message;

    UserTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserTypeEnum findUserTypeEnumByCode(int code) {
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            if (code == userTypeEnum.code) {
                return userTypeEnum;
            }
        }
        return null;
    }

    public static UserTypeEnum findUserTypeByMessage(String message) {
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            if (StringUtils.equals(message, userTypeEnum.message)) {
                return userTypeEnum;
            }
        }
        return null;
    }

}
