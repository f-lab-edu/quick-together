package com.flab.quicktogether.alarm.service;

public interface AlarmSendService<M> {

    public void sendAlarm(String token, M message);

}
