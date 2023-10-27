package com.example.ratelimiter.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Put data into Redis
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Get data from Redis
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Delete data from Redis
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}

