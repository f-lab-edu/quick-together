package com.flab.quicktogether.alarm.firebase;

import com.flab.quicktogether.alarm.message.AlarmMessage;
import com.flab.quicktogether.alarm.message.SimpleMessageDto;
import com.flab.quicktogether.alarm.service.AlarmSendService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FcmAlarmSendService implements AlarmSendService<SimpleMessageDto>{


    private Message createMessageWithToken(String token, SimpleMessageDto simpleMessageDto) {
        Message.Builder setNotification = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(simpleMessageDto.getTitle())
                                .setBody(simpleMessageDto.getBody())
                                .build());

        return setNotification.setToken(token).build();
    }

    @Override
    public void sendAlarm(String token, AlarmMessage<SimpleMessageDto> alarmMessage)  {
        Message message = createMessageWithToken(token, alarmMessage.getMessageInfo());

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message = {} ", response);
        } catch (Exception e) {
            log.info("failure sent message = {} ", e.getMessage());
            //throw new RuntimeException(e);
        }

    }


}