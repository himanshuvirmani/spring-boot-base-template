package com.springboot.demo.repository.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
public interface SetCache<V extends RedisJsonMapper> {
    void add(String key, V value);

    boolean isMemberOf(String key, V value);

    Set<V> members(String key, Class<V> clazz);

    V pop(String key, Class<V> clazz);

    void delete(String key);

    void expire(String key, long time, TimeUnit timeUnit);
}
