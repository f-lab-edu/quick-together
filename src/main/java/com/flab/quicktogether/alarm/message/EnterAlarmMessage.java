package com.flab.quicktogether.alarm.message;

import org.springframework.stereotype.Component;

@Component
public class EnterAlarmMessage implements AlarmMessage<SimpleMessageDto> {
    @Override
    public SimpleMessageDto getMessageInfo() {

        final String title = "입장 신청";
        final String body = "프로젝트에 입장 신청이 왔습니다.";

        return new SimpleMessageDto(title, body);
    }
}
