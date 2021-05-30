/**
 * 
 */
package com.nagakawa.guarantee.redis.service;

/**
 * @author LinhLH
 *
 */
public interface RedisService {
	void saveObjectToRedis(String key, Object value);
	
	void saveObjectToRedis(String key, Object value, long miliSecondsDuration);
	
	Object getObjectFromRedis(String key);
}
