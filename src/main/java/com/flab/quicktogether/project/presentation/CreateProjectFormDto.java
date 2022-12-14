package com.flab.quicktogether.project.presentation;


import com.flab.quicktogether.project.domain.MeetingMethod;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateProjectFormDto {
    private String projectName; // 프로젝트 이름
    private LocalDateTime startDateTime; // 시작일
    private LocalDateTime periodDate; // 모집기간
    private MeetingMethod meetingMethod; // 미팅 방법
    private String projectSummary; // 프로젝트 간단 설명
    private String description; // 프로젝트 상세설명

    public CreateProjectFormDto(String projectName, LocalDateTime startDateTime, LocalDateTime periodDate, MeetingMethod meetingMethod, String projectSummary, String description) {
        this.projectName = projectName;
        this.startDateTime = startDateTime;
        this.periodDate = periodDate;
        this.meetingMethod = meetingMethod;
        this.projectSummary = projectSummary;
        this.description = description;
    }
}
