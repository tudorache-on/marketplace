package com.ebs.marketplace.session;

import com.ebs.marketplace.model.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {
    @Bean
    public static JedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    public static RedisTemplate<String, Token> redisTemplate() {
        RedisTemplate<String, Token> redisTemplate = new RedisTemplate<String , Token>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
