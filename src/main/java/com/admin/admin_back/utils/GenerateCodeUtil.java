package com.admin.admin_back.utils;

import com.admin.admin_back.pojo.enums.CodeTypeEnum;

import java.util.UUID;

/**
 * 生成主键
 * @author 陈群矜
 */
public class GenerateCodeUtil {

    public static String generateCode(CodeTypeEnum codeType) {
        return "" + codeType.letter + UUID.randomUUID().toString().replace("-", "");
    }

}
