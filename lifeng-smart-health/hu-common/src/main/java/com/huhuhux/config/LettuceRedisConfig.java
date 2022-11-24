package com.huhuhux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class LettuceRedisConfig {

    private final RedisTemplate redisTemplate;
    @Autowired
    public LettuceRedisConfig(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        RedisSerializer<Object> jsonString = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jsonString);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jsonString);
        return redisTemplate;
    }
}
