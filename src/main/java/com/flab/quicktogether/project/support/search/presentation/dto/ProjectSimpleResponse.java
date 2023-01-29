package com.flab.quicktogether.project.support.search.presentation.dto;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.domain.ProjectDescriptionInfo;
import com.flab.quicktogether.project.domain.ProjectStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectSimpleResponse {

    private String projectName; // 프로젝트 이름

    private Long founder;

    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    private ProjectStatus projectStatus; // 프로젝트 상태

    private MeetingMethod meetingMethod; // 진행방식

    private Long likes; // 좋아요 수

    private Integer views; // 조회 수

    private LocalDateTime startDateTime; // 시작일

    private LocalDateTime periodDateTime; // 모집기간

    private List<SkillStack> projectSkillStacks = new ArrayList<>();

    private List<Position> recruitPositions = new ArrayList<>();



    public ProjectSimpleResponse(Project p, Long likes) {
        projectName = p.getProjectName();
        founder = p.getFounder().getId();
        startDateTime = p.getStartDateTime();
        periodDateTime = p.getPeriodDateTime();
        meetingMethod = p.getMeetingMethod();
        projectDescriptionInfo = p.getProjectDescriptionInfo();
        projectStatus = p.getProjectStatus();
        views = p.getViews();
        projectSkillStacks = p.getSkillStacks();
        recruitPositions = p.getRecruitmentPositions();
        this.likes = likes;
    }


}