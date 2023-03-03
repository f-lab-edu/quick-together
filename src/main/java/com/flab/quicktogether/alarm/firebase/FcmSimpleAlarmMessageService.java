package com.flab.quicktogether.alarm.firebase;


import com.flab.quicktogether.alarm.service.AlarmMessageService;
import com.flab.quicktogether.alarm.message.AlarmMessage;
import com.flab.quicktogether.alarm.message.SimpleMessageDto;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

public class FcmSimpleAlarmMessageService implements AlarmMessageService<Message, SimpleMessageDto> {

    @Override
    public Message createMessage(String token, AlarmMessage<SimpleMessageDto> alarmMessage){
        SimpleMessageDto messageInfo =  alarmMessage.getMessageInfo();

        com.google.firebase.messaging.Message.Builder setNotification = com.google.firebase.messaging.Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(messageInfo.getTitle())
                                .setBody(messageInfo.getBody())
                                .build());

        return setNotification.setToken(token).build();
    }



}
