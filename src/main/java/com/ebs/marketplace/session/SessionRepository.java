package com.ebs.marketplace.session;

import com.ebs.marketplace.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {

    private final RedisTemplate<String, String> template;

    @Autowired
    public SessionRepository(RedisTemplate<String, String> template) {
        this.template = template;
    }

    public void insert(String name, String token) {
        template.opsForValue().set(name, token);
    }

    public boolean existsByKey(String name, String token) {
        String value = template.opsForValue().get(name + token);
        return value != null;
    }
}
