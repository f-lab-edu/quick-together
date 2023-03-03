package com.flab.quicktogether.alarm;

import com.flab.quicktogether.alarm.kakao.KaKaoAlarmSendService;
import com.flab.quicktogether.alarm.kakao.KaKaoSimpleAlarmMessageService;
import com.flab.quicktogether.alarm.service.AlarmMessageService;
import com.flab.quicktogether.alarm.service.AlarmSendService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class KaKaoConfig {
    @Bean
    public AlarmSendService alarmSendService() {
        return new KaKaoAlarmSendService();
    }

    @Bean
    public AlarmMessageService alarmMessageService() {
        return new KaKaoSimpleAlarmMessageService();
    }
}
