package com.flab.quicktogether.alarm.service;

import com.flab.quicktogether.alarm.message.AlarmMessage;

public interface AlarmMessageService<T, M> {
    public T createMessage(String token, AlarmMessage<M> alarmMessage);
}
