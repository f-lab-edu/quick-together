package com.flab.quicktogether.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class RedisConfigTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    @DisplayName("Redis에 문자열을 저장하고 가져온다.")
    void redisTemplate() {

        final String key = "proto";
        final String value = "tokenValue";

        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(key, value); // redis set 명령어
        final String getValue = stringStringValueOperations.get(key); // redis get 명령어

        Assertions.assertEquals(value, getValue);
    }
}