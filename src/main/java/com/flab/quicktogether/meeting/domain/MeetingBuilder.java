package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.timeplan.application.ScheduleService;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;

/**
 * MeetingBuilder 생성이유
 * 객체 생성이후에 ProjectParticipant를 Meeting정책에 맞게 다시 MeetingParticipant로 assign필요함.
 *
 */
public class MeetingBuilder {

    private final Long memberId;
    private final Project project;
    private String title;
    private String description;
    private TimeBlock suggestionTime;

    private MeetingStatus meetingStatus;

    private MeetingPostMethod meetingPostMethod;

    public MeetingBuilder(Long memberId, Project project, MeetingStatus meetingStatus, MeetingPostMethod meetingPostMethod) {
        this.memberId = memberId;
        this.project = project;
        this.meetingStatus = meetingStatus;
        this.meetingPostMethod = meetingPostMethod;
    }

    public MeetingBuilder setMeetingInfo(MeetingInfo meetingInfo) {
        this.title = meetingInfo.getTitle();
        this.description = meetingInfo.getDescription();
        this.suggestionTime = meetingInfo.getSuggestionTime();
        return this;
    }

    /**
     * Meeting에 사용되는 시간이 모든 미팅참여자의 가능한 시간에 포함되는지 확인.
     * 도메인객체가 scheduleService 라는 application layer를 참조하는것이 DIP위반으로 생각되지만 객체 생성시 검증이 강제되므로 안전할것이라고 판단하여 결정.
     * scheduleService의 구현이나 정책이 바뀐다면 추후 인터페이스화해서 구현하면 문제될것이 없지 않나 생각됨.
     * project의 participant를 Meeting host와 비교하여 할당하여야 하므로,
     * @param scheduleService
     * @return 새로운 미팅
     */
    public Meeting validateAndBuild(ScheduleService scheduleService) {

        Meeting newMeeting = new Meeting(memberId, project,suggestionTime, title, description,  meetingStatus, meetingPostMethod, scheduleService);

        return newMeeting;
    }

}
