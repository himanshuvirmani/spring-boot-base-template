/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.springboot.demo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.repository.redis.RedisJsonMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class RatingCount implements Serializable,RedisJsonMapper {

    private static final long serialVersionUID = 1L;

    private Rating rating;

    private long count;

    public RatingCount(Rating rating, long count) {
        this.rating = rating;
        this.count = count;
    }

    @Override
    public String toJsonString() {
        final ObjectMapper jacksonObjectMapper = new ObjectMapper();
        try {
            return jacksonObjectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RatingCount fromJson(String json) {
        final ObjectMapper jacksonObjectMapper = new ObjectMapper();
        try {
            return jacksonObjectMapper.readValue(json, RatingCount.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
