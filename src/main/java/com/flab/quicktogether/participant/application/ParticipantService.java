package com.flab.quicktogether.participant.application;


import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.application.dto.ChangeParticipantRoleRequestDto;
import com.flab.quicktogether.participant.application.dto.EditParticipantPositionRequestDto;
import com.flab.quicktogether.participant.application.dto.EditParticipantSkillStackRequestDto;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.exception.DuplicateParticipantPositionException;
import com.flab.quicktogether.participant.exception.DuplicateParticipantSkillStackException;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import com.flab.quicktogether.globalsetting.domain.Position;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.project.exception.*;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.flab.quicktogether.globalsetting.domain.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;


    /**
     * 구성원 전체 조회
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

    /**
     * 구성원 추가
     */
    @Transactional
    public void joinProject(Long projectId, Long memberId) {

        checkProjectParticipation(projectId, memberId);

        Project project = findProject(projectId);
        Member member = findMember(memberId);
        participantRepository.save(Participant.addMember(project, member));
    }

    private void checkProjectParticipation(Long projectId, Long memberId) {
        participantRepository.findByProjectIdAndMemberId(projectId, memberId)
                .ifPresent((t) -> {throw new DuplicateProjectParticipationException();});

    }

    /**
     * 구성원 삭제
     */
    @Transactional
    public void leaveProject(Long projectId, Long memberId) {
        Participant participant = findParticipant(projectId, memberId);
        participantRepository.delete(participant);
    }

    /**
     * 구성원 권한 변경
     */
    @Transactional
    public void changeRole(Long projectId, Long memberId, ChangeParticipantRoleRequestDto changeParticipantRoleRequestDto) {
        Participant participant = findParticipant(projectId, memberId);
        participant.changeParticipantRole(changeParticipantRoleRequestDto.getParticipantRole());
    }

    /**
     * 구성원 포지션 추가
     */
    @Transactional
    public void addPosition(Long projectId, Long memberId, EditParticipantPositionRequestDto editParticipantPositionRequestDto) {

        Participant participant = findParticipant(projectId, memberId);

        validateDuplicatePosition(participant, editParticipantPositionRequestDto.getPosition());

        participant.addPosition(editParticipantPositionRequestDto.getPosition());
    }

    private void validateDuplicatePosition(Participant participant, Position newPosition) {
        List<Position> positions = participant.getPositions();
        positions.stream()
                .filter(position -> position.equals(newPosition))
                .forEach(position -> {
                    throw new DuplicateParticipantPositionException();
                });
    }

    /**
     * 구성원 포지션 삭제
     */
    @Transactional
    public void removePosition(Long projectId, Long memberId, EditParticipantPositionRequestDto editParticipantPositionRequestDto) {
        Participant participant = findParticipant(projectId, memberId);
        participant.removePosition(editParticipantPositionRequestDto.getPosition());
    }

    /**
     * 구성원 스킬스택 추가
     */
    @Transactional
    public void addSkillStack(Long projectId, Long memberId, EditParticipantSkillStackRequestDto editParticipantSkillStackRequestDto) {
        Participant participant = findParticipant(projectId, memberId);

        validateDuplicateSkillStack(participant, editParticipantSkillStackRequestDto.getSkillStack());

        participant.addSkillStack(editParticipantSkillStackRequestDto.getSkillStack());
    }

    private void validateDuplicateSkillStack(Participant participant, SkillStack newSkillStack) {
        List<SkillStack> skillStacks = participant.getSkillStacks();
        skillStacks.stream()
                .filter(skillStack -> skillStack.equals(newSkillStack))
                .forEach(skillStack -> {
                    throw new DuplicateParticipantSkillStackException();
                });
    }

    /**
     * 구성원 스킬스택 삭제
     */
    @Transactional
    public void removeSkillStack(Long projectId, Long memberId, EditParticipantSkillStackRequestDto editParticipantSkillStackRequestDto) {
        Participant participant = findParticipant(projectId, memberId);
        participant.removeSkillStack(editParticipantSkillStackRequestDto.getSkillStack());

    }

    private Participant findParticipant(Long projectId, Long memberId) {
        findProject(projectId);
        findMember(memberId);
        return participantRepository.findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(ParticipantNotFoundException::new);
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
