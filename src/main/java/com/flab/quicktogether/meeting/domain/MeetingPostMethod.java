package com.flab.quicktogether.meeting.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Event없이 Post내용을 반환할수있도록 구성된 클래스.
 * 만들어볼수록 코드 말미에 메서드가 추가되고있어 AOP를 적용하거나, EventListener도입을 해보고싶어지지만 우선 생각나는대로 구현.
 */
public enum MeetingPostMethod {
    CREATE_APPROVAL("미팅확정."),
    CREATE_DENIAL("미팅거부."),
    CREATE_REQUESTED("미팅생성이 요청됨."),
    UPDATE_APPROVAL("미팅수정이 확정됨."),
    UPDATE_DENIAL("미팅수정이 거부됨"),
    UPDATE_REQUESTED("미팅수정이 요청됨."),
    CREATE_ACCEPTED("미팅생성이 승인됨."),
    UPDATE_ACCEPTED("미팅수정요청이 승인됨."),

    PARTICIPANT_JOIN("미팅에 참여함."),
    PARTICIPANT_PROMOTE("관리자권한을 획득함."),
    PARTICIPANT_DEMOTE("일반권한으로 전환"),
    PARTICIPANT_BAN("미팅에서 제외됨."),
    PARTICIPANT_EXIT("미팅에서 퇴장함.");

    private String postMessage;

    MeetingPostMethod(String postMessage) {
        this.postMessage = postMessage;
    }

    public String createMessage(LocalDateTime meetingTime, String meetingTitle) {
        String meetingTimeString = meetingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return String.format("[ 일시 : %s, 회의주제: %s ] %s ", meetingTimeString, meetingTitle, this.postMessage);
    }

}
