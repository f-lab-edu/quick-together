package com.flab.quicktogether.project.presentation.dto.response;

import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.project.domain.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectResponse {

    @NotNull
    private String projectName; // 프로젝트 이름

    @NotNull
    private Long founder;

    @NotNull
    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    @NotNull
    private ProjectStatus projectStatus; // 프로젝트 상태

    @NotNull
    private MeetingMethod meetingMethod; // 진행방식

    @NotNull
    private Long likes; // 좋아요 수

    @NotNull
    private Integer views; // 조회 수

    @NotNull @DateTimeFormat
    private LocalDateTime startDateTime; // 시작일

    @NotNull @DateTimeFormat
    private LocalDateTime periodDateTime; // 모집기간

    @NotNull
    private List<SkillStack> skillStacks = new ArrayList<>();


    public ProjectResponse(Project p) {
        projectName = p.getProjectName();
        founder = p.getFounder().getId();
        startDateTime = p.getStartDateTime();
        periodDateTime = p.getPeriodDateTime();
        meetingMethod = p.getMeetingMethod();
        projectDescriptionInfo = p.getProjectDescriptionInfo();
        projectStatus = p.getProjectStatus();
        likes = p.getLikes();
        views = p.getViews();
        skillStacks = p.getSkillStacks();
    }
}
