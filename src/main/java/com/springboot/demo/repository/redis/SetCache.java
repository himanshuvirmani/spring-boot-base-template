package com.springboot.demo.repository.redis;

import java.util.Set;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
public interface SetCache<K, V> {
    void add(K key, V value);

    boolean isMemberOf(K key, V value);

    Set<V> members(K key);

    V pop(K key);

    void delete(K key);
}
