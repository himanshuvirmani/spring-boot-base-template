package com.springboot.demo.repository.redis;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
public interface ListCache<V extends RedisJsonMapper> {
    void push(String key, V value, boolean right);

    void multiAdd(String key, Collection<V> values, boolean right);

    Collection<V> get(String key, Class<V> clazz);

    V pop(String key, boolean right, Class<V> clazz);

    void delete(String key);

    void trim(String key, int start, int end);

    Long size(String key);

    void expire(String key, long time, TimeUnit timeUnit);
}
