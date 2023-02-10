package com.flab.quicktogether.alarm.message;

import lombok.Builder;
import lombok.Getter;


@Getter
public class SimpleMessageDto {
    private final String title;
    private final String body;

    @Builder
    public SimpleMessageDto(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
