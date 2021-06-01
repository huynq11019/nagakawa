/*
 * RedisUtil.java
 *
 * Copyright (C) 2021 by Vinsmart. All right reserved.
 * This software is the confidential and proprietary information of Vinsmart
 */
package com.nagakawa.guarantee.redis.service.impl;

import java.time.Duration;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
public class RedisServiceImpl implements RedisService {

    /** The redis template. */
    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisConfiguration redisConfiguration;

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Gets the object from redis.
     *
     * @param key
     *            the key
     * @return the object from redis
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.opsForHash().getOperations().hasKey(key);
    }

    @Override
    public Boolean hasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public void hdelete(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);

    }

    @Override
    public Object hget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public void hset(String key, HashMap<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public void hset(String key, String hashKey, Object object) {
        redisTemplate.opsForHash().put(key, hashKey, object);
    }

    @Override
    public void hset(String key, String hashKey, Object object, long miliSecondsDuration) {
        redisTemplate.opsForHash().put(key, hashKey, object);
        redisTemplate.expire(key, Duration.ofMillis(miliSecondsDuration));
    }

    @Override
    public void hset(String key, String hashKey, Object object, long duration, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, object);
        redisTemplate.expire(key, duration, timeUnit);
    }
    
    @Override
    public void hsetAbsent(String key, String hashKey, Object object) {
        redisTemplate.opsForHash().putIfAbsent(key, hashKey, object);
    }

    @Override
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * Save object to redis.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void set(String key, Object value) {
        set(key, value, redisConfiguration.getTimeout());
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
    public void set(String key, Object value, long miliSecondsDuration) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(miliSecondsDuration));
    }

}
