package com.flab.quicktogether.alarm.message;

import org.springframework.stereotype.Component;

@Component
public class InviteAlarmMessage implements AlarmMessage<NotificationSimpleMessage> {
    @Override
    public NotificationSimpleMessage createMessage() {

        final String title = "초대장";
        final String body = "프로젝트에 초대되었습니다.";

        return new NotificationSimpleMessage(title, body);
    }
}
