package com.admin.admin_back.pojo.enums;

/**
 * 生成编码时的首字母
 * @author 陈群矜
 */
public enum CodeTypeEnum {

    /**
     * 系统
     */
    SYSTEM('S'),

    /**
     * 角色
     */
    ROLE('R'),

    /**
     * 资源
     */
    RESOURCE('R'),

    /**
     * Excel映射配置
     */
    EXCEL('E'),

    /**
     * Excel上传任务
     */
    TASK('T'),

    ;

    public final char letter;

    CodeTypeEnum(char letter) {
        this.letter = letter;
    }

}
