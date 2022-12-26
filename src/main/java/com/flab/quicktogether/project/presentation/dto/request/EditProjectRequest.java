package com.flab.quicktogether.project.presentation.dto.request;

import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectRequestDto;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EditProjectRequest {

    @NotNull
    private String projectName; // 프로젝트 이름

    @NotNull @DateTimeFormat
    private LocalDateTime startDateTime; // 시작일

    @NotNull @DateTimeFormat
    private LocalDateTime periodDateTime; // 모집기간

    @NotNull
    private MeetingMethod meetingMethod; // 미팅 방법

    @NotNull
    private String projectSummary; // 프로젝트 간단 설명

    @NotNull
    private String projectDescription; // 프로젝트 상세설명

    @NotNull
    private ProjectStatus projectStatus; // 프로젝트 상태


    public EditProjectRequestDto toServiceDto() {
        EditProjectRequestDto dto = EditProjectRequestDto.builder()
                .projectName(this.getProjectName())
                .startDateTime(this.getStartDateTime())
                .periodDateTime(this.getPeriodDateTime())
                .meetingMethod(this.getMeetingMethod())
                .projectSummary(this.getProjectSummary())
                .projectDescription(this.getProjectDescription())
                .projectStatus(this.getProjectStatus())
                .build();
        return dto;
    }
}
