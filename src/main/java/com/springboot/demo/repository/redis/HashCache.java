package com.springboot.demo.repository.redis;

import java.util.List;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
public interface HashCache<K, V> {
    void put(String key, K hashkey, V obj);

    void multiPut(String key, V value);

    V get(String key, K hashkey);

    void delete(String key, K hashkey);

    List<V> getObjects(String key);

    void delete(String key);
}
