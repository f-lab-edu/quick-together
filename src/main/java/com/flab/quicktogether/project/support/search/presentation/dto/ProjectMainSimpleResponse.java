package com.flab.quicktogether.project.support.search.presentation.dto;

import com.flab.quicktogether.project.domain.Project;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectMainSimpleResponse {

    private Long projectId;
    private String projectName; // 프로젝트 이름

    private String founder;

    private String projectDescriptionInfo; // 프로젝트 설명 정보

    private Long likes; // 좋아요 수

    private Integer views; // 조회 수

    private LocalDateTime startDateTime; // 시작일
    private LocalDateTime periodDateTime; // 모집기간



    public ProjectMainSimpleResponse(Project p, Long likes) {
        projectId = p.getId();
        projectName = p.getProjectName();
        founder = p.getFounder().getMemberName();
        startDateTime = p.getStartDateTime();
        periodDateTime = p.getPeriodDateTime();
        projectDescriptionInfo = p.getProjectDescriptionInfo().getProjectSummary();
        views = p.getViews();
        this.likes = likes;
    }


}
