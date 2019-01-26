package com.william.springdata;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by william on 17-7-4.
 */
public class TopicMessageListener implements MessageListener {

    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();// 请使用valueSerializer
        byte[] channel = message.getChannel();
        // 请参考配置文件，本例中key，value的序列化方式均为string。
        // 其中key必须为stringSerializer。和redisTemplate.convertAndSend对应
        String msgContent = (String) redisTemplate.getValueSerializer().deserialize(body);
        String topic = (String) redisTemplate.getStringSerializer().deserialize(channel);
        System.out.println(" message ------ " + topic + " : " + msgContent);
    }
}
