package com.admin.admin_back.service.impl;

import com.admin.admin_back.service.DeleteCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 陈群矜
 */
@Service
public class DeleteCacheServiceImpl implements DeleteCacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Async("deleteRedisKeyExecutor")
    public void deleteRedisCache(String key, int time) {
        stringRedisTemplate.delete(key);
        while (time > 0) {
            long start = System.currentTimeMillis();
            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException ignored) {

            }
            time -= (System.currentTimeMillis() - start) / 1000;
        }
        stringRedisTemplate.delete(key);
    }

}
