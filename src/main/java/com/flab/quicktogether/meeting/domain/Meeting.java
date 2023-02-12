package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.meeting.domain.exception.AlreadyApprovedMeetingException;
import com.flab.quicktogether.meeting.domain.exception.AlreadyDeniedMeetingException;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 하나의 애그리거트로 크게 잡고 설계한 Entity
 * participant 관련 기능은 별도의 Entity로 분리하여 관리하는 편이 좀더 유지보수에 용이할것으로 보임 추후 수정예정.
 */
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

    @Transient
    private MeetingPostMethod meetingPostMethod;

    Meeting(Long memberId, Project project, TimeBlock timeBlock, String title, String description, MeetingStatus meetingStatus, MeetingPostMethod meetingPostMethod, ScheduleService scheduleService) {
        verifyProjectParticipant(memberId, project);
        assignHost(memberId, project);
        assignParticipants(project.getParticipants());
        this.project = project;
        this.meetingStatus = meetingStatus;
        setTitle(title);
        setDescription(description);

        scheduleService.validToRegist(project, timeBlock);
        this.timeBlock = timeBlock;

        this.meetingPostMethod = meetingPostMethod;

    }

    public static MeetingBuilder builder(Long memberId, Project project) {
        verifyProjectAdmin(memberId, project);

        return new MeetingBuilder(memberId, project, MeetingStatus.APPROVED, MeetingPostMethod.CREATE_APPROVAL);
    }

    public static MeetingBuilder requestBuilder(Long memberId, Project project) {
        verifyProjectParticipant(memberId, project);

        return new MeetingBuilder(memberId, project, MeetingStatus.REQUESTED, MeetingPostMethod.CREATE_REQUESTED);
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

    public void acceptCreation(Long adminMemberId) {
        verifyApproval();
        verifyProjectAdmin(adminMemberId, project);

        if (this.meetingStatus.equals(MeetingStatus.REQUESTED)) {
            this.meetingStatus = MeetingStatus.APPROVED;
        }

        this.meetingPostMethod = MeetingPostMethod.CREATE_ACCEPTED;
    }

    public void denyCreation(Long adminMemberId) {
        verifyDenial();
        verifyProjectAdmin(adminMemberId, project);

        this.meetingStatus=MeetingStatus.DENIED;

        this.meetingPostMethod=MeetingPostMethod.CREATE_DENIAL;
    }

    public void update(Long adminMemberId, MeetingInfo meetingInfo, List<Plan> plansMadeByMeeting, ScheduleService scheduleService) {
        verifyAdmin(adminMemberId);

        String title = meetingInfo.getTitle();
        String description = meetingInfo.getDescription();
        TimeBlock suggestionTime = meetingInfo.getSuggestionTime();

        setTitle(title);
        setDescription(description);
        setTimeBlock(suggestionTime, scheduleService);

        //미팅수정시에 미팅에 의해 만들어진 plan들을 가져와서 맞게 동기화시켜주어야함.
        plansMadeByMeeting
                .forEach(plan -> plan.update(this.title, this.timeBlock));

        this.meetingPostMethod=MeetingPostMethod.UPDATE_APPROVAL;

    }
    public void proposeModification(Long memberId, MeetingInfo meetingInfo) {
        verifyMeetingParticipant(memberId);
        this.meetingProposals.add(new MeetingProposal(meetingInfo));
        this.meetingPostMethod = MeetingPostMethod.UPDATE_REQUESTED;
    }

    private void update(MeetingInfo meetingInfo, ScheduleService scheduleService) {
        String title = meetingInfo.getTitle();
        String description = meetingInfo.getDescription();
        TimeBlock suggestionTime = meetingInfo.getSuggestionTime();
        setTitle(title);
        setDescription(description);
        setTimeBlock(suggestionTime, scheduleService);

        this.meetingPostMethod = MeetingPostMethod.UPDATE_APPROVAL;
    }

    public void acceptProposal(Long adminMemberId, Long meetingProposalId, ScheduleService scheduleService) {
        verifyAdmin(adminMemberId);

        MeetingProposal meetingProposal = findProposal(meetingProposalId);

        meetingProposal.approve();

        update(meetingProposal.getMeetingInfo(), scheduleService);

        this.meetingPostMethod = MeetingPostMethod.UPDATE_APPROVAL;
    }

    private MeetingProposal findProposal(Long meetingProposalId) {
        MeetingProposal meetingProposal = meetingProposals.stream()
                .filter(proposal -> proposal.getId()
                        .equals(meetingProposalId))
                .findFirst()
                .orElseThrow(MeetingProposalNotFoundException::new);
        return meetingProposal;
    }

    public void denyProposal(Long adminMemberId, Long meetingProposalId, ScheduleService scheduleService) {
        verifyAdmin(adminMemberId);

        MeetingProposal meetingProposal = findProposal(meetingProposalId);

        meetingProposal.deny();

        update(meetingProposal.getMeetingInfo(), scheduleService);

        this.meetingPostMethod = MeetingPostMethod.UPDATE_DENIAL;
    }

    public void proposeDeletion(Long loginMemberId) {
        verifyMeetingParticipant(loginMemberId);

    }
    private void verifyMeetingParticipant(Long memberId) {
        meetingParticipants.checkParticipant(memberId);
    }

    public void promote(Long adminMemberId, Long meetingParticipantId) {
        verifyAdmin(adminMemberId);

        meetingParticipants.find(meetingParticipantId)
                .promote();

        this.meetingPostMethod = MeetingPostMethod.PARTICIPANT_PROMOTE;
    }

    public void demote(Long adminMemberId, Long meetingParticipantId) {
        verifyAdmin(adminMemberId);

        meetingParticipants.find(meetingParticipantId)
                .demote();

        this.meetingPostMethod = MeetingPostMethod.PARTICIPANT_DEMOTE;
    }

    public void ban(Long adminMemberId, Long meetingParticipantId) {
        verifyApproval();
        verifyAdmin(adminMemberId);
        this.meetingParticipants
                .ban(meetingParticipantId);

        this.meetingPostMethod = MeetingPostMethod.PARTICIPANT_BAN;
    }

    public void exit(Long memberId) {
        meetingParticipants.exit(memberId);
    }

    public List<Plan> createPlans() {
        return meetingParticipants.getList().stream()
                .map(participant -> {
                    Long memberId = participant.getMember().getId();
                    return new Plan(memberId,title,timeBlock);
                })
                .toList();
    }

    public Post createPost() {
        LocalDateTime meetingDateTime = this.timeBlock.getStartDateTime();
        String createPostContent = MeetingPostMethod.CREATE_APPROVAL.createMessage(meetingDateTime, this.title);
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

    public void cancel(Long adminMemberId) {
        this.meetingStatus = MeetingStatus.CANCLED;
    }

    public void checkParticipant(Long memberId) {
        meetingParticipants.checkParticipant(memberId);
    }
}
