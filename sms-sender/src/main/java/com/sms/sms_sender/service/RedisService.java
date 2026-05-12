package com.sms.sms_sender.service;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
     private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isUserBlocked(String userId) {

        Boolean exists = redisTemplate.hasKey("blocked:" + userId);

        return exists != null && exists;
    }
}
