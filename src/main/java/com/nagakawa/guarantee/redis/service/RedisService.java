/**
 * 
 */
package com.nagakawa.guarantee.redis.service;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author LinhLH
 */
public interface RedisService {
    void delete(String key);
    
    Object get(String key);
    
    Boolean hasKey(String key);
    
    Boolean hasKey(String key, String hashKey);
    
    void hdelete(String key);
    
    Object hget(String key);
    
    Object hget(String key, String hashKey);
    
    void hset(String key, HashMap<String, Object> map);
    
    void hset(String key, String hashKey, Object object);
    
    void hset(String key, String hashKey, Object object, long miliSecondsDuration);
    
    public void hset(String key, String hashKey, Object object, long duration, TimeUnit timeUnit);
    
    void hsetAbsent(String key, String hashKey, Object object);

    Set<String> keys(String key);

    void set(String key, Object value);

    void set(String key, Object value, long miliSecondsDuration);
}
