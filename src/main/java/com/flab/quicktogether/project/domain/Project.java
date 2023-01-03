package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.globalsetting.domain.Position;
import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.timeplan.domain.etc.MinuteUnit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long id;
    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member founder;

    @Embedded
    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus = ProjectStatus.OPEN; // 프로젝트 상태

    @Enumerated(EnumType.STRING)
    private MeetingMethod meetingMethod; // 진행방식

    private Long likes = 0L; // 좋아요 수

    private Integer views = 0; // 조회 수

    private LocalDateTime startDateTime; // 시작일

    @CreationTimestamp
    private LocalDateTime createDateTime; // 생성일

    private LocalDateTime periodDateTime; // 모집기간

    @ElementCollection
    @CollectionTable(name = "ProjectSkillStack", joinColumns = @JoinColumn(name = "project_id"))
    @Enumerated(EnumType.STRING)
    private List<SkillStack> skillStacks = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "ProjectRecruitmentPosition", joinColumns = @JoinColumn(name = "project_id"))
    @Enumerated(EnumType.STRING)
    private List<Position> RecruitmentPositions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MinuteUnit meetingTimeUnit;

    @Builder
    public Project(String projectName, Member founder, String projectSummary, String projectDescription,
                   MeetingMethod meetingMethod, LocalDateTime startDateTime, LocalDateTime periodDateTime, MinuteUnit meetingTimeUnit) {

        Assert.hasText(projectName,"projectName must not be empty");
        Assert.notNull(founder, "projectFounder must not be null");
        Assert.notNull(projectSummary, "projectSummary must not be null");
        Assert.notNull(projectDescription, "description must not be null");
        Assert.notNull(meetingMethod, "meetingMethod must not be null");
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(periodDateTime, "periodDate must not be null");
        Assert.notNull(meetingMethod, "meetingTimeUnit must not be null");

        this.founder = founder;
        this.projectName = projectName;
        this.meetingMethod = meetingMethod;
        this.startDateTime = startDateTime;
        this.periodDateTime = periodDateTime;
        this.meetingTimeUnit = meetingTimeUnit;

        this.projectDescriptionInfo = new ProjectDescriptionInfo(projectSummary, projectDescription);
    }

    public static Project createProject(String projectName, Member founder, String projectSummary, String description,
                              MeetingMethod meetingMethod, LocalDateTime startDateTime, LocalDateTime periodDate){
        Project project = new Project();

        project.founder = founder;
        project.projectName = projectName;
        project.meetingMethod = meetingMethod;
        project.startDateTime = startDateTime;
        project.periodDateTime = periodDate;

        project.projectDescriptionInfo = new ProjectDescriptionInfo(projectSummary, description);

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
        this.projectDescriptionInfo = new ProjectDescriptionInfo(editProjectSummary, projectDescriptionInfo.getProjectDescription());
    }

    public void changeProjectDescription(String editProjectDescription) {
        this.projectDescriptionInfo = new ProjectDescriptionInfo(projectDescriptionInfo.getProjectSummary(),editProjectDescription);
    }

    public void changeProjectDescriptionInfo(ProjectDescriptionInfo editProjectDescriptionInfo){
        this.projectDescriptionInfo = editProjectDescriptionInfo;
    }

    public void settingMeetingTimeUnit(MinuteUnit minuteUnit) {
        this.meetingTimeUnit = minuteUnit;
    }

    public void settingLikes(Long likes) {
        this.likes = likes;
    }


    public Participant registerFounder(Member findMember, Project project) {
        return new Participant(findMember, project, ParticipantRole.ROLE_ADMIN);
    }

    public void addSkillStack(SkillStack skillStack) {
        this.getSkillStacks().add(skillStack);
    }

    public void removeSkillStack(SkillStack skillStack) {
        this.getSkillStacks().remove(skillStack);
    }

    public void addRecruitmentPosition(Position recruitmentPosition) {
        this.getRecruitmentPositions().add(recruitmentPosition);
    }

    public void removeRecruitmentPosition(Position recruitmentPosition) {
        this.getRecruitmentPositions().remove(recruitmentPosition);
    }
}


