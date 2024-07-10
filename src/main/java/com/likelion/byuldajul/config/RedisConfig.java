package com.likelion.byuldajul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}") //yml파일에서 host 읽어옴
    private String redisHost;

    @Value("${spring.data.redis.port}") //yml파일에서 port 읽어옴
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(); // Lettuce 사용하여 Redis 연결 설정
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory); // Redis 연결 설정
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // Redis 키 직렬화 설정
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // Redis 값 직렬화 설정
        return redisTemplate;
    }
}
