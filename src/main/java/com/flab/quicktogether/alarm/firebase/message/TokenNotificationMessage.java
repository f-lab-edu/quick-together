package com.flab.quicktogether.alarm.firebase.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenNotificationMessage {

    private final String Token;
    private final NotificationMessage notificationMessage;

}
