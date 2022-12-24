package com.flab.quicktogether.participant.application;


import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
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
import com.flab.quicktogether.participant.presentation.dto.request.EditParticipantPositionRequest;
import com.flab.quicktogether.participant.presentation.dto.request.ChangeParticipantRoleRequest;
import com.flab.quicktogether.participant.presentation.dto.request.EditParticipantSkillStackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.flab.quicktogether.globalsetting.exception.ErrorCode.*;

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

    private void checkProjectParticipation(Long projectId, Long memberId){
        Optional<Participant> participant = participantRepository.findByProjectIdAndMemberId(projectId, memberId);
        if (participant.isPresent()) {
            throw new DuplicateProjectParticipationException(DUPLICATE_PROJECT_PARTICIPATION);
        }
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
    public void changeRole(Long projectId, Long memberId, ChangeParticipantRoleRequest changeParticipantRoleRequest) {
        Participant participant = findParticipant(projectId, memberId);
        participant.changeParticipantRole(changeParticipantRoleRequest.getParticipantRole());
    }

    /**
     * 구성원 포지션 추가
     */
    @Transactional
    public void addPosition(Long projectId, Long memberId, EditParticipantPositionRequest editParticipantPositionRequest){

        Participant participant = findParticipant(projectId, memberId);

        validateDuplicatePosition(participant, editParticipantPositionRequest.getPosition());

        participant.addPosition(editParticipantPositionRequest.getPosition());
    }

    private void validateDuplicatePosition(Participant participant, Position newPosition) {
        List<Position> positions = participant.getPositions();
        positions.stream()
                .filter(position -> position.equals(newPosition))
                .forEach(position -> {
            throw new DuplicateParticipantPositionException(DUPLICATE_PARTICIPANT_POSITION);
        });
    }

    /**
     * 구성원 포지션 삭제
     */
    @Transactional
    public void removePosition(Long projectId, Long memberId, EditParticipantPositionRequest editParticipantPositionRequest){
        Participant participant = findParticipant(projectId, memberId);
        participant.removePosition(editParticipantPositionRequest.getPosition());
    }

    /**
     * 구성원 스킬스택 추가
     */
    @Transactional
    public void addSkillStack(Long projectId, Long memberId, EditParticipantSkillStackRequest editParticipantSkillStackRequest){
        Participant participant = findParticipant(projectId, memberId);

        validateDuplicateSkillStack(participant, editParticipantSkillStackRequest.getSkillStack());

        participant.addSkillStack(editParticipantSkillStackRequest.getSkillStack());
    }

    private void validateDuplicateSkillStack(Participant participant, SkillStack newSkillStack) {
        List<SkillStack> skillStacks = participant.getSkillStacks();
        skillStacks.stream()
                .filter(skillStack -> skillStack.equals(newSkillStack))
                .forEach(skillStack -> {
                    throw new DuplicateParticipantSkillStackException(DUPLICATE_PARTICIPANT_SKILLSTACK);
                });
    }

    /**
     * 구성원 스킬스택 삭제
     */
    @Transactional
    public void removeSkillStack(Long projectId, Long memberId, EditParticipantSkillStackRequest editParticipantSkillStackRequest) {
        Participant participant = findParticipant(projectId, memberId);
        participant.removeSkillStack(editParticipantSkillStackRequest.getSkillStack());

    }

    private Participant findParticipant(Long projectId, Long memberId) {
        findProject(projectId);
        findMember(memberId);
        Optional<Participant> participant = participantRepository.findByProjectIdAndMemberId(projectId, memberId);
        if (!participant.isPresent()) {
            throw new ParticipantNotFoundException(PARTICIPANT_NOT_FOUND);
        }
        return participant.get();
    }

    private Project findProject(Long projectId) {
        Optional<Project> project = projectRepository.findOne(projectId);
        if (!project.isPresent()) {
            throw new ProjectNotFoundException(PROJECT_NOT_FOUND);
        }
        return project.get();
    }

    private Member findMember(Long memberId) {
        Optional<Member> member = memberRepository.findOne(memberId);
        if (!member.isPresent()) {
            //throw new MemberNotFoundException(String.format("MemberId[%s] not found", memberId));
            throw new MemberNotFoundException(MEMBER_NOT_FOUND);
        }
        return member.get();
    }
}
