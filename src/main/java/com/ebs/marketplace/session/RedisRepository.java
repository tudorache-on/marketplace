package com.ebs.marketplace.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository {

    @Value("${token.timeout}")
    private int timeout;
    private final RedisTemplate<String, String> template;

    public RedisRepository (RedisTemplate<String, String> redisTemplate) {
        this.template = redisTemplate;
    }

    public void save(String id, String token) {
        template.opsForValue().set(id, token, timeout, TimeUnit.MINUTES);
    }

    public String getByKey(String id) {
        return template.opsForValue().get(id);
    }
}
