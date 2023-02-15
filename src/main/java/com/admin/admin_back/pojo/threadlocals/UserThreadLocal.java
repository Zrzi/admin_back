package com.admin.admin_back.pojo.threadlocals;

public class UserThreadLocal {

    private final static ThreadLocal<String> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserToken(String token) {
        USER_THREAD_LOCAL.set(token);
    }

    public static String getUserToken() {
        return USER_THREAD_LOCAL.get();
    }

    public static void removeUserToken() {
        USER_THREAD_LOCAL.remove();
    }

}
