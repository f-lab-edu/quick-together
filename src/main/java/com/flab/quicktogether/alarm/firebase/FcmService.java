package com.flab.quicktogether.alarm.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FcmService {

    public Message createMessageWithToken(TokenMessageRequest tokenMessageRequest){
        Message.Builder setNotification = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(tokenMessageRequest.getNotificationMessage().getTitle())
                                .setBody(tokenMessageRequest.getNotificationMessage().getBody())
                                .build());

        Message message = setNotification.setToken(tokenMessageRequest.getToken()).build();
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