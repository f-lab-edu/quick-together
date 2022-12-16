package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
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

    private LocalDateTime periodDateTime; // 모집기간

    private Project(){

    }

    @Builder
    public Project(String projectName, String projectSummary, String description,
                   MeetingMethod meetingMethod, LocalDateTime startDateTime, LocalDateTime periodDateTime) {

        Assert.hasText(projectName,"projectName must not be empty");
        Assert.notNull(projectSummary, "projectSummary must not be null");
        Assert.notNull(description, "description must not be null");
        Assert.notNull(meetingMethod, "meetingMethod must not be null");
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(periodDateTime, "periodDate must not be null");

        this.projectName = projectName;
        this.meetingMethod = meetingMethod;
        this.startDateTime = startDateTime;
        this.periodDateTime = periodDateTime;

        this.projectDescriptionInfo = new ProjectDescriptionInfo(projectSummary, description);
        this.createDateTime = LocalDateTime.now();
    }

    public static Project createProject(String projectName, String projectSummary, String description,
                              MeetingMethod meetingMethod, LocalDateTime startDateTime, LocalDateTime periodDate){
        Project project = new Project();

        project.projectName = projectName;
        project.meetingMethod = meetingMethod;
        project.startDateTime = startDateTime;
        project.periodDateTime = periodDate;

        project.projectDescriptionInfo = new ProjectDescriptionInfo(projectSummary, description);
        project.createDateTime = LocalDateTime.now();

        return project;
    }

    public void changeProjectName(String editProjectName){
        this.projectName = editProjectName;
    }

    public void changeStartDateTime(LocalDateTime editStartDateTime) {
        this.startDateTime = editStartDateTime;
    }

    public void changePeriodDate(LocalDateTime editPeriodDate) {
        this.periodDateTime = editPeriodDate;
    }

    public void changeMeetingMethod(MeetingMethod editMeetingMethod){
        this.meetingMethod = editMeetingMethod;
    }

    public void changeProjectStatus(ProjectStatus editProjectStatus){
        this.projectStatus = editProjectStatus;
    }

    public void changeProjectSummary(String editProjectSummary){
        this.projectDescriptionInfo = new ProjectDescriptionInfo(editProjectSummary, projectDescriptionInfo.getDescription());
    }

    public void changeProjectDescription(String editProjectDescription) {
        this.projectDescriptionInfo = new ProjectDescriptionInfo(projectDescriptionInfo.getProjectSummary(),editProjectDescription);
    }

    public void changeProjectDescriptionInfo(ProjectDescriptionInfo editProjectDescriptionInfo){
        this.projectDescriptionInfo = editProjectDescriptionInfo;
    }


    public Participant registerFounder(Member findMember, Project project) {
        return new Participant(findMember, project, ParticipantRole.ROLE_ADMIN);
    }
}


