package com.flab.quicktogether.alarm.service;

import com.flab.quicktogether.alarm.exception.AlarmTokenNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisAlarmTokenServiceTest {

    @Autowired
    AlarmTokenService alarmTokenService;

    @Test
    @DisplayName("알람토큰을 저장하고 가져온다.")
    void saveTokenAndGetToken() {
        String savedToken = "token";
        Long memberId = 1L;

        alarmTokenService.saveToken(memberId, savedToken);
        String findToken = alarmTokenService.getToken(memberId);
        Assertions.assertEquals(findToken,savedToken);
    }

    @Test
    @DisplayName("알람토큰을 가지고 있지 않는 멤버로 조회하면 Exception을 발생시킨다.")
    void getToken() {
        Assertions.assertThrows(AlarmTokenNotFoundException.class, () -> alarmTokenService.getToken(0L));

    }


}