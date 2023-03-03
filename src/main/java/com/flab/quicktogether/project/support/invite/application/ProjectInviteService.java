package com.flab.quicktogether.project.support.invite.application;


import com.flab.quicktogether.project.support.invite.event.ProjectInviteEvent;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.project.event.Events;
import com.flab.quicktogether.project.support.invite.domain.Invite;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.support.invite.exception.DuplicateInviteMemberException;
import com.flab.quicktogether.project.support.invite.exception.InviteNotFoundException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.support.invite.infrastructure.InviteRepository;
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
    private final InviteRepository inviteRepository;


    public Invite retrieveInvitedMember(Long projectId, Long invitedMemberId) {
        Invite invite = findInvite(projectId, invitedMemberId);
        return invite;
    }

    @Transactional
    public void inviteMember(Long projectId, Long requestMemberId, Long invitedMemberId) {
        Project project = findProject(projectId);
        project.getParticipants().checkParticipantNot(invitedMemberId);
        project.getParticipants().checkAdminAuth(requestMemberId);

        checkInvitedNot(projectId, invitedMemberId);

        Member requestMember = findMember(requestMemberId);
        Member invitedMember = findMember(invitedMemberId);
        inviteRepository.save(Invite.inviteMember(project, requestMember, invitedMember));

        Events.raise(new ProjectInviteEvent(projectId, invitedMemberId));
    }

    private void checkInvitedNot(Long projectId, Long invitedMemberId) {
        inviteRepository.findByProjectIdAndInvitedMemberIdWithWait(projectId, invitedMemberId)
                .ifPresent(invitedMember -> {
                    throw new DuplicateInviteMemberException();
                });
    }

    @Transactional
    public void acceptInvite(Long projectId, Long invitedMemberId) {
        Invite invite = findInvite(projectId, invitedMemberId);
        invite.accept();
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
                .orElseThrow(InviteNotFoundException::new);
        return invite;
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
