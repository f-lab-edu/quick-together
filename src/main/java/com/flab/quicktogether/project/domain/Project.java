package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.common.CUDTimeStampInfo;
import com.flab.quicktogether.common.TimeSection;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public class Project {
    private Long id;
    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보
    private MeetingMethod meetingMethod; // 진행방식

    private Integer participantCapacity; // 참여정원
    private List<Participant> participants; // 참여인원

    private LocalDateTime startDateTime; // 시작일
    private Long periodDate; // 모집기간

    private ProjectStatus projectStatus; // 프로젝트 상태

    private Set<DayOfWeek> regularAvailableMeetingDayOfWeek; // 가능요일
    private List<TimeSection> regularAvailableMeetingTimes; // 가능시간
    private List<Role> recruitingRoles; // 모집중인 역할

    private Integer likes; // 좋아요 수
    private Integer views; // 조회수
    private CUDTimeStampInfo projectTimeStampInfo; // 생성, 수정, 삭제에 대한 TimeStamp 정보

//    private AlarmSettingInfo 추후 알람기능 생성시


}

@Getter
@Setter
class ProjectDescriptionInfo {
    private String projectName; // 프로젝트 이름
    private String founderName; // 주최자 이름
    private String projectSummary; // 프로젝트 간단 설명
    private String description; // 프로젝트 상세설명

}

enum MeetingMethod {
    OFFLINE, DISCORD, GOOGLE_MEET, SLACK
}

enum ProjectStatus {
    OPEN, CLOSED, DELETED
}
