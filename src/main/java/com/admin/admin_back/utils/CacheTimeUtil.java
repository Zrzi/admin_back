package com.admin.admin_back.utils;

import com.admin.admin_back.pojo.constant.Constant;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author 陈群矜
 */
@Component
public class CacheTimeUtil {

    private final Random random = new Random();

    /**
     * 生成10-15秒的随机时间，防止缓存雪崩
     * @return 缓存时间
     */
    public int getCacheTime() {
        return Constant.INT_10 + random.nextInt(5);
    }

}
