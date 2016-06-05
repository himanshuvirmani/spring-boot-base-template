package com.springboot.demo.repository.redis;

import java.util.HashMap;

/**
 * Created by himanshu.virmani on 04/06/16.
 */
public interface RedisHashMapper {
    HashMap<String, Object> toMap();
}
