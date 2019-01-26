package com.william.springdata;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by william on 17-7-4.
 */
public class PubSubService {

    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String channel, Object object) {
        redisTemplate.convertAndSend(channel, object);
    }

}
