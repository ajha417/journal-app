package com.app.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("position", "Software Engineer");
        Object position = redisTemplate.opsForValue().get("position");
        System.out.println( position);
    }
}
