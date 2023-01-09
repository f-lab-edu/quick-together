package com.flab.quicktogether.project.application.dto;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateProjectRequestDto {
    private Long memberId;
    private String projectName; // 프로젝트 이름
    private LocalDateTime startDateTime; // 시작일
    private LocalDateTime periodDateTime; // 모집기간
    private MeetingMethod meetingMethod; // 미팅 방법
    private String projectSummary; // 프로젝트 간단 설명
    private String projectDescription; // 프로젝트 상세설명


    @Builder
    public CreateProjectRequestDto(Long memberId, String projectName, LocalDateTime startDateTime, LocalDateTime periodDateTime, MeetingMethod meetingMethod, String projectSummary, String projectDescription) {
        this.memberId = memberId;
        this.projectName = projectName;
        this.startDateTime = startDateTime;
        this.periodDateTime = periodDateTime;
        this.meetingMethod = meetingMethod;
        this.projectSummary = projectSummary;
        this.projectDescription = projectDescription;
    }

    public Project createProject(Member member){
        Project project = Project.builder()
                .projectName(this.getProjectName())
                .founder(member)
                .startDateTime(this.getStartDateTime())
                .periodDateTime(this.getPeriodDateTime())
                .meetingMethod(this.getMeetingMethod())
                .projectSummary(this.getProjectSummary())
                .projectDescription(this.getProjectDescription())
                .build();
        return project;
    }

}
