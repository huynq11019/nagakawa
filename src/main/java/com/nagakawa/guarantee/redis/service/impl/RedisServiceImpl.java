/*
 * RedisUtil.java
 *
 * Copyright (C) 2021 by Vinsmart. All right reserved.
 * This software is the confidential and proprietary information of Vinsmart
 */
package com.nagakawa.guarantee.redis.service.impl;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.nagakawa.guarantee.redis.RedisConfiguration;
import com.nagakawa.guarantee.redis.service.RedisService;

import lombok.RequiredArgsConstructor;

/**
 * The Class RedisUtil.
 */
@Component
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{

    /** The redis template. */
    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisConfiguration redisConfiguration;
    /**
     * Save object to redis.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void saveObjectToRedis(String key, Object value) {
        saveObjectToRedis(key, value, redisConfiguration.getTimeout());
    }

    /**
     * Save object to redis.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @param secondsDuration
     *            the seconds duration
     */
    public void saveObjectToRedis(String key, Object value, long miliSecondsDuration) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(miliSecondsDuration));
    }

    /**
     * Gets the object from redis.
     *
     * @param key
     *            the key
     * @return the object from redis
     */
    public Object getObjectFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
