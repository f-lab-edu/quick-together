package com.flab.quicktogether.project.presentation.dto.request;


import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateProjectDto {

    @NotNull
    private Long memberId;

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




}
