package com.flab.quicktogether.alarm.firebase.message;

import lombok.Builder;
import lombok.Getter;


@Getter
public class NotificationMessage {
    private String title;
    private String body;

    @Builder
    public NotificationMessage(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
