package com.flab.quicktogether.alarm.service;

import com.flab.quicktogether.alarm.message.AlarmMessage;

public interface AlarmSendService<T> {

    public void sendAlarm(String token, AlarmMessage<T> message);

}
