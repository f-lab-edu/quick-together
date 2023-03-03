package com.flab.quicktogether.alarm.service;

import com.flab.quicktogether.alarm.exception.AlarmTokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisAlarmTokenService implements AlarmTokenService{

    @Autowired
    private RedisTemplate<Long, Object> redisTemplate;


    public void saveToken(Long memberId, String value) {
        redisTemplate.opsForValue().set(memberId, value);
    }


    public String getToken(Long memberId) {
        Object token = redisTemplate.opsForValue().get(memberId);
        if (token == null) {
            throw new AlarmTokenNotFoundException();
        }

        return String.valueOf(token);
    }

}
