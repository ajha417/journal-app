package com.app.journalapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> clazz) {
        try {
            Object object = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            assert object != null;
            return objectMapper.readValue(object.toString(), clazz);
        } catch (Exception e) {
            log.error("Error getting value from redis", e);
            return null;
        }
    }
}
