package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.participant.domain.Participants;
import com.flab.quicktogether.project.exception.JoinProjectException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


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
    private Participants participants = new Participants();

    @Embedded
    private ProjectDescriptionInfo projectDescriptionInfo; // 프로젝트 설명 정보

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus = ProjectStatus.OPEN; // 프로젝트 상태

    @Enumerated(EnumType.STRING)
    private MeetingMethod meetingMethod; // 진행방식

    private Integer views = 0; // 조회 수

    private LocalDateTime startDateTime; // 시작일

    @CreationTimestamp
    private LocalDateTime createDateTime; // 생성일

    private LocalDateTime periodDateTime; // 모집기간

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ProjectSkillStack", joinColumns = @JoinColumn(name = "project_id"))
    @Enumerated(EnumType.STRING)
    private Set<SkillStack> skillStacks = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ProjectRecruitmentPosition", joinColumns = @JoinColumn(name = "project_id"))
    @Enumerated(EnumType.STRING)
    private Set<Position> recruitmentPositions = new HashSet<>();

    @Builder()
    public Project(String projectName, Member founder, String projectSummary, String projectDescription,
                   MeetingMethod meetingMethod, LocalDateTime startDateTime, LocalDateTime periodDateTime,
                   Set<SkillStack> skillStacks, Set<Position> recruitmentPositions) {

        Assert.hasText(projectName,"projectName must not be empty");
        Assert.notNull(founder, "projectFounder must not be null");
        Assert.notNull(projectSummary, "projectSummary must not be null");
        Assert.notNull(projectDescription, "description must not be null");
        Assert.notNull(meetingMethod, "meetingMethod must not be null");
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(periodDateTime, "periodDate must not be null");

        this.founder = founder;
        this.projectName = projectName;
        this.meetingMethod = meetingMethod;
        this.startDateTime = startDateTime;
        this.periodDateTime = periodDateTime;
        this.skillStacks = skillStacks;
        this.recruitmentPositions = recruitmentPositions;

        this.projectDescriptionInfo = new ProjectDescriptionInfo(projectSummary, projectDescription);

    }

    public void plusViews(){
        this.views = this.views + 1;
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

    public void checkJoinProject(){
        if(projectStatus.equals(ProjectStatus.OPEN)){
            //do nothing
        }else {
           throw new JoinProjectException();
        }
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


