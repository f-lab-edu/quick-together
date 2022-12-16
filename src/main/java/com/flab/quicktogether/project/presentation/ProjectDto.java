package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.domain.ProjectDescriptionInfo;
import com.flab.quicktogether.project.domain.ProjectStatus;

import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ProjectDto {

    @NonNull
    private String projectName; // 프로젝트 이름

    @NonNull
    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    @NonNull
    private ProjectStatus projectStatus; // 프로젝트 상태

    @NonNull
    private MeetingMethod meetingMethod; // 진행방식

    @NonNull
    private Integer likes; // 좋아요 수

    @NonNull
    private Integer views; // 조회 수

    @NonNull @DateTimeFormat
    private LocalDateTime startDateTime; // 시작일

    @NonNull @DateTimeFormat
    private LocalDateTime periodDateTime; // 모집기간


    public ProjectDto(Project p) {

        projectName = p.getProjectName();
        startDateTime = p.getStartDateTime();
        periodDateTime = p.getPeriodDateTime();
        meetingMethod = p.getMeetingMethod();
        projectDescriptionInfo = p.getProjectDescriptionInfo();
        projectStatus = p.getProjectStatus();
        likes = p.getLikes();
        views = p.getViews();

    }
}
