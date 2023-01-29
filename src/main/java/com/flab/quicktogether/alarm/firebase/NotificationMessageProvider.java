package com.flab.quicktogether.alarm.firebase;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

public class NotificationMessageProvider {
    public static Message inviteMember(String token){

        String title = "초대장";
        String body = "프로젝트에 초대되었습니다.";

        Message message = getMessage(token, title, body);
        return message;
    }

    public static Message enterMember(String token){

        String title = "입장 신청";
        String body = "프로젝트에 입장 신청이 왔습니다.";

        Message message = getMessage(token, title, body);
        return message;
    }

    private static Message getMessage(String token, String title, String body) {
        Message.Builder setNotification = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build());

        Message message = setNotification.setToken(token).build();
        return message;
    }
}
