package com.flab.quicktogether.alarm.service;

public interface AlarmTokenService {


    public void saveToken(Long memberId, String value);
    public String getToken(Long memberId);

}
