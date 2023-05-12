package com.admin.admin_back.service;

/**
 * @author 陈群矜
 */
public interface DeleteCacheService {

    /**
     * 延迟删除缓存
     * @param key redis键
     * @param time 时间
     */
    void deleteRedisCache(String key, int time);

}
