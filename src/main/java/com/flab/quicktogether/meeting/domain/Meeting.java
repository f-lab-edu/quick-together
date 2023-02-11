package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.meeting.domain.exception.AlreadyApprovedMeetingException;
import com.flab.quicktogether.meeting.domain.exception.AlreadyDeniedMeetingException;
import com.flab.quicktogether.meeting.domain.exception.MeetingPostIllegalStateException;
import com.flab.quicktogether.meeting.domain.exception.MeetingProposalNotFoundException;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.participant.domain.Participants;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.support.post.domain.Post;
import com.flab.quicktogether.timeplan.application.ScheduleService;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.persistence.*;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Getter
public class Meeting {

    @Id
    @GeneratedValue
    @Column(name = "meeting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member host;

    @Embedded
    private MeetingParticipants meetingParticipants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    private String title;

    @Lob
    private String description;

    @Embedded
    private TimeBlock timeBlock;

    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingProposal> meetingProposals = new ArrayList<>();

    Meeting(Long memberId, Project project, TimeBlock timeBlock, String title, String description, MeetingStatus meetingStatus, ScheduleService scheduleService) {
        verifyProjectParticipant(memberId, project);
        assignHost(memberId, project);
        assignParticipants(project.getParticipants());
        this.project = project;
        this.meetingStatus = meetingStatus;
        setTitle(title);
        setDescription(description);

        scheduleService.validToRegist(project, timeBlock);
        this.timeBlock = timeBlock;
    }

    public static MeetingBuilder builder(Long memberId, Project project) {
        verifyProjectAdmin(memberId, project);

        return new MeetingBuilder(memberId, project, MeetingStatus.APPROVED);
    }

    public static MeetingBuilder requestBuilder(Long memberId, Project project) {
        verifyProjectParticipant(memberId, project);

        return new MeetingBuilder(memberId, project, MeetingStatus.REQUESTED);
    }


    private void assignHost(Long memberId, Project project) {
        this.host = project
                .getParticipants()
                .findParticipant(memberId)
                .getMember();
    }

    /**
     * Project Participant를 Meeting에 할당
     * Meeting 생성의 host와 프로젝트의 admin은 모두 Meeting의 admin이 됨.
     * @param participants
     */
    private void assignParticipants(Participants participants) {
        List<Participant> participantList = participants.participantsInfo();
        List<MeetingParticipant> meetingParticipants = participantList.stream()
                .map(participant -> MeetingParticipant.assignProjectParticipant(host, participant))
                .toList();

        this.meetingParticipants.assignParticipants(meetingParticipants);
    }

    public void joinParticipant(Long adminMemberId, Member member, ParticipantRole authority) {
        verifyAdmin(adminMemberId);
        this.meetingParticipants.assignParticipant(member, authority);
    }

    public void approve(Long adminMemberId) {
        verifyApproval();
        verifyProjectAdmin(adminMemberId, project);

        if (this.meetingStatus.equals(MeetingStatus.REQUESTED)) {
            this.meetingStatus=MeetingStatus.APPROVED;
        }
    }

    public void deny(Long adminMemberId) {
        verifyDenial();
        verifyProjectAdmin(adminMemberId, project);

        this.meetingStatus=MeetingStatus.DENIED;
    }

    public void update(Long adminMemberId, MeetingInfo meetingInfo, ScheduleService scheduleService) {
        verifyAdmin(adminMemberId);

        String title = meetingInfo.getTitle();
        String description = meetingInfo.getDescription();
        TimeBlock suggestionTime = meetingInfo.getSuggestionTime();

        setTitle(title);
        setDescription(description);
        setTimeBlock(suggestionTime, scheduleService);
    }
    public void update(Long adminMemberId, Long meetingProposalId, ScheduleService scheduleService) {
        verifyAdmin(adminMemberId);

        MeetingProposal meetingProposal = meetingProposals.stream()
                .filter(proposal -> proposal.getId()
                        .equals(meetingProposalId))
                .findFirst()
                .orElseThrow(MeetingProposalNotFoundException::new);

        meetingProposal.approve();
        String title = meetingProposal.getTitle();
        String description = meetingProposal.getDescription();
        TimeBlock suggestionTime = meetingProposal.getTimeBlock();

        setTitle(title);
        setDescription(description);
        setTimeBlock(suggestionTime, scheduleService);
    }

    public void promote(Long adminMemberId, Long meetingParticipantId) {
        verifyAdmin(adminMemberId);

        meetingParticipants.find(meetingParticipantId)
                .promote();
    }

    public void demote(Long adminMemberId, Long meetingParticipantId) {
        verifyAdmin(adminMemberId);

        meetingParticipants.find(meetingParticipantId)
                .demote();
    }

    public void ban(Long adminMemberId, Long toBeDeletedParticipantId) {
        verifyApproval();
        verifyAdmin(adminMemberId);
        this.meetingParticipants
                .ban(toBeDeletedParticipantId);
    }

    public List<Plan> createPlans() {
        return meetingParticipants.getMeetingParticipantList().stream()
                .map(participant -> {
                    Long memberId = participant.getMember().getId();
                    return new Plan(memberId,title,timeBlock);
                })
                .toList();
    }

    public Post createPost() {
        String startDateTime = this.timeBlock.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String createPostContent = createPostContent(startDateTime);

        return Post.createPost(this.project,host, createPostContent);
    }

    private void verifyAdmin(Long memberId) {
        this.meetingParticipants.checkAdmin(memberId);
    }

    private static void verifyProjectAdmin(Long adminMemberId, Project project) {
        project.getParticipants()
                .checkAdminAuth(adminMemberId);
    }

    private static void verifyProjectParticipant(Long adminMemberId, Project project) {
        project.getParticipants()
                .checkParticipant(adminMemberId);
    }
    private void verifyApproval() {
        if (this.meetingStatus.equals(MeetingStatus.APPROVED)) {
            throw new AlreadyApprovedMeetingException();
        }
    }

    private void verifyDenial() {
        if (this.meetingStatus.equals(MeetingStatus.DENIED)) {
            throw new AlreadyDeniedMeetingException();
        }
    }
    private void setTimeBlock(TimeBlock timeBlock, ScheduleService scheduleService) {
        scheduleService.validToModify(this.id, project, timeBlock);
        this.timeBlock = timeBlock;
    }

    private String createPostContent(String startDateTime) {
        switch (this.meetingStatus) {
            case APPROVED:
                return String.format("%s 미팅예정, 미팅주제 : %s", startDateTime, this.title);
            case REQUESTED:
                return String.format("%s 미팅제안, 미팅주제 : %s", startDateTime, this.title);
            default :
                throw new MeetingPostIllegalStateException();
        }

    }

    private void setTitle(String title) {
        this.title = nullChecked(title);
    }

    private void setDescription(String description) {
        this.description= nullChecked(description);
    }


    private String nullChecked(String string) {
        if (string == null) {
            return "";
        }
        return string;
    }

    public boolean equals(Plan plan) {
        String planName = plan.getPlanName();
        TimeBlock timeBlock = plan.getTimeBlock();

        return this.title.equals(planName)
                && this.timeBlock.equals(timeBlock);
    }
}
