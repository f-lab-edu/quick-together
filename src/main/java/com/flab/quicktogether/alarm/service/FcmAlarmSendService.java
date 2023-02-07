package com.flab.quicktogether.alarm.service;

import com.flab.quicktogether.alarm.firebase.TokenNotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FcmAlarmSendService {

    public Message createMessageWithToken(TokenNotificationMessage tokenNotificationMessage){
        Message.Builder setNotification = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(tokenNotificationMessage.getNotificationMessage().getTitle())
                                .setBody(tokenNotificationMessage.getNotificationMessage().getBody())
                                .build());

        Message message = setNotification.setToken(tokenNotificationMessage.getToken()).build();
        return message;
    }

    public void sendAlarm(Message message)  {
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message = {} ", response);
        } catch (Exception e) {
            log.info("failure sent message = {} ", e.getMessage());
            //throw new RuntimeException(e);
        }

    }

}