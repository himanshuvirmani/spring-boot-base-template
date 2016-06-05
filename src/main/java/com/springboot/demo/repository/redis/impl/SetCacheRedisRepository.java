package com.springboot.demo.repository.redis.impl;

import com.springboot.demo.repository.redis.SetCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
@Component("setCacheRedisRepository")
public class SetCacheRedisRepository implements SetCache<String,String> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void add(String key, String value) {
        redisTemplate.opsForSet().add(key,value);
    }

    @Override
    public boolean isMemberOf(String key, String value) {
        return redisTemplate.opsForSet().isMember(key,value);
    }

    @Override
    public Set<String> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public String pop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
