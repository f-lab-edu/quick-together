package com.flab.quicktogether.project.application;


import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.application.ParticipantService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.domain.Enter;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.DuplicateEnterMemberException;
import com.flab.quicktogether.project.exception.DuplicateProjectParticipationException;
import com.flab.quicktogether.project.exception.EnterNotFoundException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.EnterRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectEnterService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final EnterRepository enterRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantService participantService;


    public Enter retrieveEnterMember(Long projectId, Long enterMemberId){
        Enter Enter = findEnter(projectId, enterMemberId);
        return Enter;
    }

    /**
     * 프로젝트에 입장 신청
     */
    @Transactional
    public void requestEnterProject(Long projectId, Long enterMemberId) {
        checkProjectParticipation(projectId, enterMemberId);
        isEnter(projectId, enterMemberId);

        Member EnterMember = findMember(enterMemberId);
        Project project = findProject(projectId);
        enterRepository.save(Enter.enterMember(project, EnterMember));
    }

    private void checkProjectParticipation(Long projectId, Long memberId) {
        participantRepository.findByProjectIdAndMemberId(projectId, memberId).ifPresent(joinedParticipant -> {
            throw new DuplicateProjectParticipationException();
        });
    }

    private void isEnter(Long projectId, Long enterMemberId) {
        enterRepository.findByProjectIdAndEnterMemberIdWithWait(projectId, enterMemberId).ifPresent(enterMember ->{
            throw new DuplicateEnterMemberException();
        });
    }

    @Transactional
    public void acceptEnter(Long projectId, Long adminMemberId, Long EnterMemberId) {

        Participant adminParticipant = findParticipant(projectId, adminMemberId);
        adminParticipant.checkPermission();

        Enter Enter = findEnter(projectId, EnterMemberId);
        Enter.accept();

        participantService.joinProject(projectId,EnterMemberId);
    }

    private Participant findParticipant(Long projectId, Long longId) {
        return participantRepository.findByProjectIdAndMemberId(projectId, longId)
                .orElseThrow(ParticipantNotFoundException::new);
    }

    @Transactional
    public void rejectEnter(Long projectId, Long adminMemberId, Long EnterMemberId) {

        Participant adminParticipant = findParticipant(projectId, adminMemberId);
        adminParticipant.checkPermission();

        Enter Enter = findEnter(projectId, EnterMemberId);
        Enter.reject();
    }

    private Enter findEnter(Long projectId, Long enterMemberId) {
        findProject(projectId);
        findMember(enterMemberId);
        Enter enter = enterRepository.findByProjectIdAndEnterMemberIdWithWait(projectId, enterMemberId)
                .orElseThrow(EnterNotFoundException::new);
        return enter;
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
