package com.admin.admin_back.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author 陈群矜
 */
public class Md5Util {

    public static String encrypt(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean verify(String password, String passwordFromDb) {
        return StringUtils.equals(passwordFromDb, encrypt(password));
    }

}
