package com.springboot.demo.repository.redis.impl;

import com.springboot.demo.repository.redis.ListCache;
import com.springboot.demo.repository.redis.RedisJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
@Component("listCacheRedisRepository")
public class ListCacheRedisRepository<V extends RedisJsonMapper> implements ListCache<V> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void push(String key, V value, boolean right) {
        if (right) {
            redisTemplate.opsForList().rightPush(key, value.toJsonString());
        } else {
            redisTemplate.opsForList().leftPush(key, value.toJsonString());
        }
    }

    @Override
    public void multiAdd(String key, Collection<V> values, boolean right) {
        //  redisTemplate.multi();
        //Have to find better implementation for this
        for (V value : values) {
            push(key, value, right);
        }
        // redisTemplate.exec();
    }

    @Override
    public Collection<V> get(String key, Class<V> clazz) {
        List<String> list = redisTemplate.opsForList().range(key, 0, -1);
        if(list == null && list.isEmpty()) return null;
        List<V> collection = new ArrayList<>();
        for (String item : list) {
            try {
                collection.add((V)clazz.newInstance().fromJson(item));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return collection;
    }

    @Override
    public V pop(String key, boolean right, Class<V> clazz) {
        String value;
        if (right) {
            value = redisTemplate.opsForList().rightPop(key);
        } else {
            value = redisTemplate.opsForList().leftPop(key);
        }
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
    public void trim(String key, int start, int end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    @Override
    public Long size(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public void expire(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }
}
