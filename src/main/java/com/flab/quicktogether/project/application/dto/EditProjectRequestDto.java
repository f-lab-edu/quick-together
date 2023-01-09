package com.flab.quicktogether.project.application.dto;

import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class EditProjectRequestDto {

    private String projectName; // 프로젝트 이름
    private LocalDateTime startDateTime; // 시작일
    private LocalDateTime periodDateTime; // 모집기간
    private MeetingMethod meetingMethod; // 미팅 방법
    private String projectSummary; // 프로젝트 간단 설명
    private String projectDescription; // 프로젝트 상세설명
    private ProjectStatus projectStatus; // 프로젝트 상태

    @Builder
    public EditProjectRequestDto(String projectName, LocalDateTime startDateTime, LocalDateTime periodDateTime, MeetingMethod meetingMethod, String projectSummary, String projectDescription, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.startDateTime = startDateTime;
        this.periodDateTime = periodDateTime;
        this.meetingMethod = meetingMethod;
        this.projectSummary = projectSummary;
        this.projectDescription = projectDescription;
        this.projectStatus = projectStatus;
    }
}
