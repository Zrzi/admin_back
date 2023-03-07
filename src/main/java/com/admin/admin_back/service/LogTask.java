package com.admin.admin_back.service;

/**
 * @author 陈群矜
 */
public interface LogTask {

    /**
     * 在LogAspect中调用，异步打印执行方法前的信息
     * @param userNo 用户名
     * @param methodName 执行的方法
     * @param args 参数
     */
    void logBeforeMethod(String userNo, String methodName, Object[] args);

    /**
     * 在LogAspect中调用，异步打印执行方法后的信息
     * @param userNo 用户名
     * @param methodName 执行的方法
     * @param result 返回结果
     */
    void logAfterMethod(String userNo, String methodName, Object result);

    void logInfo(String info);

}
