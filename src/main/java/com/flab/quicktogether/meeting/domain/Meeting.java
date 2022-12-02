package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.common.CUDTimeStampInfo;
import com.flab.quicktogether.common.TimeSection;

import java.time.LocalDate;


public class Meeting {
    Long id;
    Long meetingNo; // 클라이언트가 볼수있는 미팅고유번호
    String meetingName; // 미팅이름
    LocalDate meetingDate; // 미팅시작일
    TimeSection meetingTime; // 미팅시간

    String description; // 상세설명

    CUDTimeStampInfo meetingTimeStampInfo; // 생성, 수정, 삭제에 대한 TimeStamp 정보

    //    private AlarmSettingInfo 추후 알람기능 생성시
}

enum MeetingStatus {
    APPROVED, DENIED, VOTING, STARTED, CLOSED
}
