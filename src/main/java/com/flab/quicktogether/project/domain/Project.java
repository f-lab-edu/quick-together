package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.presentation.EditProjectFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;


@Getter
@Entity
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long id;
    private String projectName;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "founder_id")
    private Founder founder;

    @Embedded
    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus = ProjectStatus.OPEN; // 프로젝트 상태

    @Enumerated(EnumType.STRING)
    private MeetingMethod meetingMethod; // 진행방식

    private Integer likes = 0; // 좋아요 수

    private Integer views = 0; // 조회 수

    private LocalDateTime startDateTime; // 시작일
    private LocalDateTime createDateTime; // 생성일

    private Long periodDate; // 모집기간

    private Project(){

    }

    @Builder
    public Project(String projectName, Member founder, String projectSummary, String description,
                   MeetingMethod meetingMethod, LocalDateTime startDateTime, Long periodDate) {

        Assert.hasText(projectName,"projectName must not be empty");
        Assert.notNull(founder, "founder must not be null");
        Assert.notNull(projectSummary, "projectSummary must not be null");
        Assert.notNull(description, "description must not be null");
        Assert.notNull(meetingMethod, "meetingMethod must not be null");
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(periodDate, "periodDate must not be null");

        this.projectName = projectName;
        this.meetingMethod = meetingMethod;
        this.startDateTime = startDateTime;
        this.periodDate = periodDate;

        this.founder = new Founder(founder);
        this.projectDescriptionInfo = new ProjectDescriptionInfo(projectSummary, description);
        this.createDateTime = LocalDateTime.now();
    }
    public void editProject(EditProjectFormDto editProjectForm){
        this.changeProjectName(editProjectForm.getProjectName());
        this.projectDescriptionInfo.changeProjectSummary(editProjectForm.getProjectSummary());
        this.projectDescriptionInfo.changeProjectDescription(editProjectForm.getProjectDescription());
        this.changeStartDateTime(editProjectForm.getStartDateTime());
        this.changePeriodDate(editProjectForm.getPeriodDate());
        this.changeProjectStatus(editProjectForm.getProjectStatus());
    }

    public void changeProjectName(String editProjectName){
        this.projectName = editProjectName;
    }

    public void changeStartDateTime(LocalDateTime editStartDateTime) {
        this.startDateTime = editStartDateTime;
    }

    public void changePeriodDate(Long editPeriodDate) {
        this.periodDate = editPeriodDate;
    }

    public void changeProjectStatus(ProjectStatus editProjectStatus){
        this.projectStatus = editProjectStatus;
    }


    public void changeProjectFounder(Member changeFounder){
        this.founder = new Founder(changeFounder);
    }




}


