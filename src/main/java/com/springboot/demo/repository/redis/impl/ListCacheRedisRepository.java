package com.springboot.demo.repository.redis.impl;

import com.springboot.demo.repository.redis.ListCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
@Component("listCacheRedisRepository")
public class ListCacheRedisRepository implements ListCache<String, String> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void push(String key, String value, boolean right) {
        if(right) {
            redisTemplate.opsForList().rightPush(key,value);
        }
        else {
            redisTemplate.opsForList().leftPush(key,value);
        }
    }

    @Override
    public void multiAdd(String key, Collection<String> values, boolean right) {
        //  redisTemplate.multi();
        //Have to find better implementation for this
        for(String value : values) {
            push(key,value,right);
        }
        // redisTemplate.exec();
    }

    @Override
    public Collection<String> get(String key) {
        return redisTemplate.opsForList().range(key,0,-1);
    }

    @Override
    public String pop(String key, boolean right) {
        String value;
        if(right) {
            value = redisTemplate.opsForList().rightPop(key);
        }
        else {
            value = redisTemplate.opsForList().leftPop(key);
        }
        return  value;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void trim(String key, int start, int end) {
        redisTemplate.opsForList().trim(key,start,end);
    }

    @Override
    public Long size(String key) {
        return redisTemplate.opsForList().size(key);
    }
}
