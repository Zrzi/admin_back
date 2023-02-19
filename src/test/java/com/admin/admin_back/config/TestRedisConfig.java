package com.admin.admin_back.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisConfig {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("test-key", "test-value", 100, TimeUnit.SECONDS);
    }

}
