package com.flab.quicktogether.alarm.firebase;

import com.flab.quicktogether.alarm.service.AlarmSendService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FcmAlarmSendService implements AlarmSendService<Message>{
    @Override
    public void sendAlarm(String token, Message message)  {
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message = {} ", response);
        } catch (Exception e) {
            log.info("failure sent message = {} ", e.getMessage());
            //throw new RuntimeException(e);
        }

    }


}