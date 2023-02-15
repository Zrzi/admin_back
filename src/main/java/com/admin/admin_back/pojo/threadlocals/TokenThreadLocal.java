package com.admin.admin_back.pojo.threadlocals;

/**
 * @author 陈群矜
 */
public class TokenThreadLocal {

    private final static ThreadLocal<String> TOKEN_THREAD_LOCAL = new ThreadLocal<>();

    public static void setTokenThreadLocal(String token) {
        TOKEN_THREAD_LOCAL.set(token);
    }

    public static String getToken() {
        return TOKEN_THREAD_LOCAL.get();
    }

    public static void removeToken() {
        TOKEN_THREAD_LOCAL.remove();
    }

}
