package com.flab.quicktogether.meeting.domain;

public enum MeetingStatus {
    APPROVED("승인된 미팅"), DENIED("거부된 미팅"), REQUESTED("미팅 요청");

    private String message;

    MeetingStatus(String message) {
    }

    public String getMessage() {
        return this.message;
    }
}
