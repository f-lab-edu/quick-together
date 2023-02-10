package com.flab.quicktogether.alarm.firebase;

import com.flab.quicktogether.alarm.message.AlarmMessage;
import com.flab.quicktogether.alarm.message.NotificationSimpleMessage;
import com.flab.quicktogether.alarm.service.AlarmSendService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FcmAlarmSendService implements AlarmSendService<NotificationSimpleMessage>{


    private Message createMessageWithToken(String token, NotificationSimpleMessage notificationSimpleMessage) {
        Message.Builder setNotification = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(notificationSimpleMessage.getTitle())
                                .setBody(notificationSimpleMessage.getBody())
                                .build());

        return setNotification.setToken(token).build();
    }

    @Override
    public void sendAlarm(String token, AlarmMessage<NotificationSimpleMessage> alarmMessage)  {
        Message message = createMessageWithToken(token, alarmMessage.createMessage());

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message = {} ", response);
        } catch (Exception e) {
            log.info("failure sent message = {} ", e.getMessage());
            //throw new RuntimeException(e);
        }

    }


}