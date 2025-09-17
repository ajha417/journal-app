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


    /**
     * Retrieves a value from Redis for the given key and converts it to the specified target type.
     * <p>
     * If the key does not exist, this method returns {@code null}. The actual
     * serialization/deserialization behavior depends on the configured serializers.
     *
     * @param key        the Redis key to look up; must not be {@code null} or blank
     * @param clazz the expected type of the stored value; must not be {@code null}
     * @param <T>        the generic type to return
     * @return the value mapped to {@code key} converted to {@code targetType}, or {@code null} if the key is absent
     * @throws IllegalArgumentException                                      if {@code key} or {@code targetType} is invalid
     * @throws org.springframework.dao.DataAccessException                    if a low-level Redis access error occurs
     * @throws org.springframework.data.redis.serializer.SerializationException if serialization or deserialization fails
     * @throws ClassCastException                                             if the stored value cannot be converted to {@code targetType}
     */
    public <T> T get(String key, Class<T> clazz) {
        log.debug("get()::Enter");
        try {
            Object object = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            assert object != null;
            log.debug("get()::Exit");
            return objectMapper.readValue(object.toString(), clazz);
        } catch (Exception e) {
            log.error("Error getting value from redis", e);
            return null;
        }
    }

    public void set(String key, Object value, Long ttl) {
        log.debug("set()::Enter");
        try {
            redisTemplate.opsForValue().set(key, value, ttl);
        } catch (Exception e) {
            log.error("Error setting value in redis", e);
        }
        log.debug("set()::Exit");
    }
}
