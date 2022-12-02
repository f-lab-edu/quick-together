package com.flab.quicktogether.project.presentation;


import java.time.LocalDateTime;

public class ProjectCreateRequestDto {
    private String projectName; // 프로젝트 이름
    private String founderName; // 주최자 이름
    private String projectSummary; // 프로젝트 간단 설명
    private String description; // 프로젝트 상세설명

    private String meetingMethod;
    private Integer participantCapacity; // 참여정원

    private LocalDateTime startDateTime; // 시작일
    private Long periodDate; // 모집기간
}
