package com.admin.admin_back.pojo.enums;

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

}
