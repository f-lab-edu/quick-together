package com.flab.quicktogether.alarm.message;

import lombok.Builder;
import lombok.Getter;


@Getter
public class NotificationSimpleMessage {
    private final String title;
    private final String body;

    @Builder
    public NotificationSimpleMessage(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
