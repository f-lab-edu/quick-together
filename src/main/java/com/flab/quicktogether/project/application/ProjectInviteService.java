package com.flab.quicktogether.project.application;


import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.application.ParticipantService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.domain.Invite;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.DuplicateInviteMemberException;
import com.flab.quicktogether.project.exception.DuplicateProjectParticipationException;
import com.flab.quicktogether.project.exception.InviteNotFoundException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.InviteRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectInviteService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;
    private final InviteRepository inviteRepository;
    private final ParticipantService participantService;


    public Invite retrieveInvitedMember(Long projectId, Long invitedMemberId){
        Invite invite = findInvite(projectId, invitedMemberId);
        return invite;
    }

    @Transactional
    public void inviteMember(Long projectId, Long requestMemberId, Long invitedMemberId) {
        Member requestMember = findMember(requestMemberId);
        Member invitedMember = findMember(invitedMemberId);
        Project project = findProject(projectId);
        checkProjectParticipation(projectId, invitedMemberId);

        Participant requestParticipant = findParticipant(projectId, requestMemberId);
        requestParticipant.checkPermission();

        isInvited(projectId, invitedMemberId);
        inviteRepository.save(Invite.inviteMember(project, requestMember, invitedMember));
    }

    private void checkProjectParticipation(Long projectId, Long memberId) {
        participantRepository.findByProjectIdAndMemberId(projectId, memberId).ifPresent(joinedParticipant -> {
            throw new DuplicateProjectParticipationException();
        });
    }

    private void isInvited(Long projectId, Long invitedMemberId) {
        inviteRepository.findByProjectIdAndInvitedMemberIdWithWait(projectId, invitedMemberId).ifPresent(invitedMember ->{
            throw new DuplicateInviteMemberException();
        });
    }

    @Transactional
    public void acceptInvite(Long projectId, Long invitedMemberId) {
        Invite invite = findInvite(projectId, invitedMemberId);
        invite.accept();

        participantService.joinProject(projectId,invitedMemberId);
    }

    @Transactional
    public void rejectInvite(Long projectId, Long invitedMemberId) {
        Invite invite = findInvite(projectId, invitedMemberId);
        invite.reject();
    }

    private Invite findInvite(Long projectId, Long invitedMemberId) {
        findProject(projectId);
        findMember(invitedMemberId);
        Invite invite = inviteRepository.findByProjectIdAndInvitedMemberIdWithWait(projectId, invitedMemberId)
                .orElseThrow(() -> new InviteNotFoundException());
        return invite;
    }

    private Participant findParticipant(Long projectId, Long longId) {
        return participantRepository.findByProjectIdAndMemberId(projectId, longId).orElseThrow(
                () -> new ParticipantNotFoundException());
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException());
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException());
    }
}
