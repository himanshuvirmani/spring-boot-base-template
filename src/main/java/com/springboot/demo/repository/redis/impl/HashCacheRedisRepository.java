package com.springboot.demo.repository.redis.impl;

import com.springboot.demo.repository.redis.HashCache;
import com.springboot.demo.repository.redis.RedisHashMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
@Component("hashCacheRedisRepository")
public class HashCacheRedisRepository<K, V extends RedisHashMapper> implements HashCache<K, V> {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void put(String key, K hashkey, V value) {
        redisTemplate.opsForHash().put(key, hashkey, value);
    }

    @Override
    public void multiPut(String key, V obj) {
        redisTemplate.opsForHash().putAll(key, obj.toMap());
    }

    @Override
    public V get(String key, K hashkey) {
        return (V) redisTemplate.opsForHash().get(key, hashkey);
    }

    @Override
    public void delete(String key, K hashkey) {
        redisTemplate.opsForHash().delete(key, hashkey);
    }

    @Override
    public List<V> getObjects(String key) {
        List<V> objs = redisTemplate.opsForHash().values(key).stream().map(obj -> (V) obj).collect(Collectors.toList());
        return objs;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
