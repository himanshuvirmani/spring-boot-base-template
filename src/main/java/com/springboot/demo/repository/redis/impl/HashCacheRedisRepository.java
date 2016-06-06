package com.springboot.demo.repository.redis.impl;

import com.springboot.demo.repository.redis.HashCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
@Component("hashCacheRedisRepository")
public class HashCacheRedisRepository<V> implements HashCache<V> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final Logger log = LoggerFactory.getLogger(HashCacheRedisRepository.class);


    HashMapper<V, String, String> mapper;

    @Override
    public void put(String key, String hashkey, String value) {
        redisTemplate.opsForHash().put(key, hashkey, value);
    }

    @Override
    public void multiPut(String key, V obj) {
        if(mapper == null) {
            mapper = new DecoratingStringHashMapper<>(new JacksonHashMapper<>((Class<V>)
                    ((ParameterizedType)obj.getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0]));
        }
        redisTemplate.opsForHash().putAll(key, mapper.toHash(obj));
    }

    @Override
    public String get(String key, String hashkey) {
        return (String) redisTemplate.opsForHash().get(key, hashkey);
    }

    @Override
    public void delete(String key, String hashkey) {
        redisTemplate.opsForHash().delete(key, hashkey);
    }

    @Override
    public V multiGet(String key, Class<V> clazz) {
        if(mapper == null) {
            mapper = new DecoratingStringHashMapper<>(new JacksonHashMapper<>(clazz));
        }
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        if(map == null || map.isEmpty()) return null;
        V obj = mapper.fromHash((HashMap<String, String>) (Map) map);
        return obj;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
