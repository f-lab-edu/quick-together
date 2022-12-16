package com.flab.quicktogether.project.presentation;


import com.flab.quicktogether.project.domain.MeetingMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateProjectFormDto {

    @NonNull
    private String projectName; // 프로젝트 이름

    @NonNull @DateTimeFormat
    private LocalDateTime startDateTime; // 시작일

    @NonNull @DateTimeFormat
    private LocalDateTime periodDateTime; // 모집기간

    @NonNull
    private MeetingMethod meetingMethod; // 미팅 방법

    @NonNull
    private String projectSummary; // 프로젝트 간단 설명

    @NonNull
    private String projectDescription; // 프로젝트 상세설명

}
