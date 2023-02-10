package com.flab.quicktogether.alarm.firebase;

import com.flab.quicktogether.alarm.message.NotificationMessage;
import com.flab.quicktogether.alarm.service.AlarmSendService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FcmAlarmSendService implements AlarmSendService<NotificationMessage> {


    private Message createMessageWithToken(String token, NotificationMessage notificationMessage) {
        Message.Builder setNotification = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(notificationMessage.getTitle())
                                .setBody(notificationMessage.getBody())
                                .build());

        Message message = setNotification.setToken(token).build();
        return message;
    }

    @Override
    public void sendAlarm(String token, NotificationMessage notificationMessage)  {
        Message message = createMessageWithToken(token, notificationMessage);

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message = {} ", response);
        } catch (Exception e) {
            log.info("failure sent message = {} ", e.getMessage());
            //throw new RuntimeException(e);
        }

    }

}
