package com.admin.admin_back.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author 陈群矜
 */
public class Md5Util {

    /**
     * 生成MD5摘要
     * @param text 原始文本
     * @return 文本摘要
     */
    public static String digest(String text) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
            digest = Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException ignored) {

        }
        return digest;
    }

    public static String encrypt(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean verify(String password, String passwordFromDb) {
        return StringUtils.equals(passwordFromDb, encrypt(password));
    }

}
