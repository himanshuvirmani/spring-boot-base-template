package com.springboot.demo.repository.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
public interface ValueCache<V extends RedisJsonMapper> {
    void put(String key, V value);

    void multiPut(Map<String, String> keyValues);

    V get(String key, Class<V> clazz);

    List<String> multiGet(Collection<String> keys);

    void delete(String key);

}
