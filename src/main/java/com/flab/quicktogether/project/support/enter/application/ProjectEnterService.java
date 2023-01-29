package com.flab.quicktogether.project.support.enter.application;


import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.project.event.Events;
import com.flab.quicktogether.project.support.enter.domain.Enter;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.support.enter.event.ProjectEnterEvent;
import com.flab.quicktogether.project.support.enter.exception.DuplicateEnterMemberException;
import com.flab.quicktogether.project.support.enter.exception.EnterNotFoundException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.support.enter.infrastructure.EnterRepository;
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


    public Enter retrieveEnterMember(Long projectId, Long enterMemberId) {
        Enter Enter = findEnter(projectId, enterMemberId);
        return Enter;
    }

    /**
     * 프로젝트에 입장 신청
     */
    @Transactional
    public void requestEnterProject(Long projectId, Long enterMemberId) {
        Project project = findProject(projectId);
        project.getParticipants().checkParticipantNot(enterMemberId);

        checkEnteredNot(projectId, enterMemberId);

        Member EnterMember = findMember(enterMemberId);
        enterRepository.save(Enter.enterMember(project, EnterMember));

        Events.raise(new ProjectEnterEvent(projectId, enterMemberId));
    }

    private void checkEnteredNot(Long projectId, Long enterMemberId) {
        enterRepository.findByProjectIdAndEnterMemberIdWithWait(projectId, enterMemberId)
                .ifPresent(enterMember -> {
                    throw new DuplicateEnterMemberException();
                });
    }

    @Transactional
    public void acceptEnter(Long projectId, Long adminMemberId, Long EnterMemberId) {
        Project project = findProject(projectId);
        project.getParticipants().checkAdminAuth(adminMemberId);

        Enter Enter = findEnter(projectId, EnterMemberId);
        Enter.accept();
    }

    @Transactional
    public void rejectEnter(Long projectId, Long adminMemberId, Long EnterMemberId) {
        Project project = findProject(projectId);
        project.getParticipants().checkAdminAuth(adminMemberId);

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
