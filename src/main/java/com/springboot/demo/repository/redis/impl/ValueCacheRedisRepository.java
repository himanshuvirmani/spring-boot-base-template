package com.springboot.demo.repository.redis.impl;

import com.springboot.demo.repository.redis.RedisJsonMapper;
import com.springboot.demo.repository.redis.ValueCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
@Component("valueCacheRedisRepository")
public class ValueCacheRedisRepository<V extends RedisJsonMapper> implements ValueCache<V> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void put(String key, V value) {
        redisTemplate.opsForValue().set(key, value.toJsonString());
    }

    @Override
    public void multiPut(Map<String, String> keyValues) {
        redisTemplate.opsForValue().multiSet(keyValues);
    }

    @Override
    public V get(String key, Class<V> clazz) {
        try {
            final String json = redisTemplate.opsForValue().get(key);

            if (json == null) return null;
            // this below is returning RedisJsonMapper because this reflection only works
            // for superclass only
/*            return (V) ((Class<V>) GenericTypeResolver.
                    resolveTypeArgument(getClass(), ValueCacheRedisRepository.class)).
                    newInstance().fromJson(json);*/
            return (V) (clazz.newInstance().fromJson(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
