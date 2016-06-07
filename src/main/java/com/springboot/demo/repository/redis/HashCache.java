package com.springboot.demo.repository.redis;


import java.util.concurrent.TimeUnit;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
public interface HashCache<V> {
    void put(String key, String hashkey, String value);

    void multiPut(String key, V value);

    String get(String key, String hashkey);

    void delete(String key, String hashkey);

    V multiGet(String key, Class<V> clazz);

    void delete(String key);

    void expire(String key, long time, TimeUnit timeUnit);
}
