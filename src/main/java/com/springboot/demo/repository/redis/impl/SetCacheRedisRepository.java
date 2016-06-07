package com.springboot.demo.repository.redis.impl;

import com.springboot.demo.repository.redis.RedisJsonMapper;
import com.springboot.demo.repository.redis.SetCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
@Component("setCacheRedisRepository")
public class SetCacheRedisRepository<V extends RedisJsonMapper> implements SetCache<V> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void add(String key, V value) {
        redisTemplate.opsForSet().add(key,value.toJsonString());
    }

    @Override
    public boolean isMemberOf(String key, V value) {
        return redisTemplate.opsForSet().isMember(key,value.toJsonString());
    }

    @Override
    public Set<V> members(String key, Class<V> clazz) {
        Set<String> strings = redisTemplate.opsForSet().members(key);
        if (strings == null || strings.isEmpty()) return null;
        Set<V> set = new HashSet<>();
        for (String value : strings) {
            try {
                set.add((V)clazz.newInstance().fromJson(value));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    @Override
    public V pop(String key, Class<V> clazz) {
        final String value = redisTemplate.opsForSet().pop(key);
        if (value == null || value.isEmpty()) return null;
        try {
            return (V)clazz.newInstance().fromJson(value);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void expire(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }
}
