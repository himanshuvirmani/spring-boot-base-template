package com.springboot.demo.repository.redis;

import java.util.Collection;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
public interface ListCache<K, V> {
    void push(K key, V value, boolean right);

    void multiAdd(K key, Collection<V> values, boolean right);

    Collection<V> get(K key);

    V pop(K key, boolean right);

    void delete(K key);

    void trim(K key, int start, int end);

    Long size(K key);
}
