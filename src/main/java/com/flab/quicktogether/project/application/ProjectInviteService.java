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
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.InviteRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.flab.quicktogether.globalsetting.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectInviteService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;
    private final InviteRepository inviteRepository;
    private final ParticipantService participantService;

    @Transactional
    public void inviteMember(Long projectId, Long requestLongId, Long invitedLongId) {
        Participant participant = findParticipant(projectId, requestLongId);
        participant.checkPermission();

        Invite invite = Invite.inviteMember(projectId, requestLongId, invitedLongId);
        inviteRepository.save(invite);
    }

    @Transactional
    public void acceptInvite(Long projectId, Long invitedMemberId) {
        Invite invite = inviteRepository.findByProjectIdAndMemberId(projectId, invitedMemberId)
                .orElseThrow(() -> new RuntimeException());
        invite.accept();

        participantService.joinProject(projectId,invitedMemberId);
    }

    @Transactional
    public void rejectInvite(Long projectId, Long invitedMemberId) {
        Invite invite = inviteRepository.findByProjectIdAndMemberId(projectId, invitedMemberId)
                .orElseThrow(() -> new RuntimeException());
        invite.reject();
    }



    private Participant findParticipant(Long projectId, Long longId) {
        findProject(projectId);
        findMember(longId);
        return participantRepository.findByProjectIdAndMemberId(projectId, longId).orElseThrow(
                () -> new ParticipantNotFoundException(PARTICIPANT_NOT_FOUND));
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException(PROJECT_NOT_FOUND));
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }
}
