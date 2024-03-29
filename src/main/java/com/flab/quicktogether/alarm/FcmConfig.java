package com.flab.quicktogether.alarm;

import com.flab.quicktogether.alarm.firebase.FcmAlarmSendService;
import com.flab.quicktogether.alarm.firebase.FcmSimpleAlarmMessageService;
import com.flab.quicktogether.alarm.service.AlarmMessageService;
import com.flab.quicktogether.alarm.service.AlarmSendService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FcmConfig {
    @Bean
    public AlarmSendService alarmSendService() {
        return new FcmAlarmSendService();
    }

    @Bean
    public AlarmMessageService alarmMessageService() {
        return new FcmSimpleAlarmMessageService();
    }
}
