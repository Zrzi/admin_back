package com.admin.admin_back.pojo.threadlocals;

import com.admin.admin_back.pojo.dto.UserDto;

public class UserThreadLocal {

    private final static ThreadLocal<UserDto> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(UserDto user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static UserDto getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }

}
