package com.springboot.demo.repository.redis;

/**
 * Created by himanshu.virmani on 05/06/16.
 */
public interface RedisJsonMapper<T> {

    String toJsonString();

    T fromJson(String json);
}
