package com.flab.quicktogether.alarm.message;

public interface NotificationContentProvider<T> {
    public T inviteMember();
    public T enterMember();
}
