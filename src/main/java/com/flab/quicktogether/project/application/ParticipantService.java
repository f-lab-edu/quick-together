package com.flab.quicktogether.project.application;


import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.MemberNotFoundException;
import com.flab.quicktogether.project.exception.ParticipantNotFoundException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.presentation.EditParticipantPositionDto;
import com.flab.quicktogether.project.presentation.EditParticipantSkillStackDto;
import com.flab.quicktogether.project.presentation.ChangeParticipantRoleDto;
import com.flab.quicktogether.project.presentation.ParticipantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;


    /**
     * 구성원들 조회
     */
    public List<Participant> retrieveAllParticipants(Long projectId) {
        return participantRepository.findByProjectId(projectId);
    }

    /**
     * 구성원 조회
     */
    public Participant retrieveParticipant(Long projectId, Long memberId) {
        Participant participant = findParticipant(projectId, memberId);
        return participant;
    }

    private Participant findParticipant(Long projectId, Long memberId) {
        Optional<Participant> participant = participantRepository.findByMemberIdAndProjectId(projectId, memberId);
        if (!participant.isPresent()) {
            throw new ParticipantNotFoundException(String.format(("Participant not found by projectId[%s] and memberId[%s]"),projectId,memberId));
        }
        return participant.get();
    }

    /**
     * 구성원 추가
     */
    public void joinProject(Long projectId, Long memberId) {
        Project project = findProject(projectId);
        Member member = findMember(memberId);
        Participant.addMember(project, member);
    }

    /**
     * 구성원 삭제
     */
    public void leaveProject(Long projectId, Long memberId) {
        Participant participant = findParticipant(projectId, memberId);
        participantRepository.delete(participant);
    }


    private Project findProject(Long projectId) {
        Optional<Project> project = projectRepository.findOne(projectId);
        if (!project.isPresent()) {
            throw new ProjectNotFoundException(String.format("ProjectId[%s] not found", projectId));
        }
        return project.get();
    }

    private Member findMember(Long memberId) {
        Optional<Member> member = memberRepository.findOne(memberId);
        if (!member.isPresent()) {
            throw new MemberNotFoundException(String.format("MemberId[%s] not found", memberId));
        }
        return member.get();
    }



}
