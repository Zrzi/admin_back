package com.admin.admin_back.service.impl;

import com.admin.admin_back.service.LogTask;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 陈群矜
 */
@Service
public class LogTaskImpl implements LogTask {

    private final Logger logger = LogManager.getLogger(LogTaskImpl.class);

    @Override
    @Async("logTaskExecutor")
    public void logBeforeMethod(String userNo, String methodName, Object[] args) {
        if (StringUtils.isBlank(userNo)) {
            logger.info("执行方法{}，参数是{}", methodName, JSON.toJSONString(args));
        } else {
            logger.info("用户名：{}，执行方法：{}，参数：{}", userNo, methodName, JSON.toJSONString(args));
        }
    }

    @Override
    @Async("logTaskExecutor")
    public void logAfterMethod(String userNo, String methodName, Object result) {
        if (StringUtils.isBlank(userNo)) {
            logger.info("执行方法：{}，返回结果：{}", methodName, JSON.toJSONString(result));
        } else {
            logger.info("用户名：{}，执行方法：{}，返回结果：{}", userNo, methodName, JSON.toJSONString(result));
        }
    }

}
