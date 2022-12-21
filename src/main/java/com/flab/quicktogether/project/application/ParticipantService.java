package com.flab.quicktogether.project.application;


import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.presentation.EditParticipantPositionDto;
import com.flab.quicktogether.project.presentation.EditParticipantSkillStackDto;
import com.flab.quicktogether.project.presentation.ChangeParticipantRoleDto;
import com.flab.quicktogether.project.presentation.ParticipantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;


    public Participant findParticipant(ParticipantDto participantDto) {
        Participant participant = participantRepository.findByMemberIdAndProjectId(participantDto.getMemberId(), participantDto.getProjectId());
        return participant;
    }

    @Transactional
    public void joinProject(ParticipantDto participantDto){

        Optional<Member> member = memberRepository.findOne(participantDto.getMemberId());
        Optional<Project> project = projectRepository.findOne(participantDto.getProjectId());

        Participant.addMember(member.get(), project.get());

    }

    @Transactional
    public void leaveProject(ParticipantDto participantDto){
        Participant participant = participantRepository.findByMemberIdAndProjectId(participantDto.getMemberId(), participantDto.getProjectId());
        participantRepository.delete(participant);

    }

    @Transactional
    public void changeRole(ChangeParticipantRoleDto changeParticipantRoleDto) {
        Participant participant = participantRepository.findByMemberIdAndProjectId(changeParticipantRoleDto.getMemberId(), changeParticipantRoleDto.getProjectId());
        participant.changeParticipantRole(changeParticipantRoleDto.getParticipantRole());
    }

    @Transactional
    public void addPosition(EditParticipantPositionDto editParticipantPositionDto){
        Participant participant = participantRepository.findByMemberIdAndProjectId(editParticipantPositionDto.getMemberId(), editParticipantPositionDto.getProjectId());
        participant.addPosition(editParticipantPositionDto.getPosition());
    }

    @Transactional
    public void addSkillStack(EditParticipantSkillStackDto editParticipantSkillStackDto){
        Participant participant = participantRepository.findByMemberIdAndProjectId(editParticipantSkillStackDto.getMemberId(), editParticipantSkillStackDto.getProjectId());
        participant.addSkillStack(editParticipantSkillStackDto.getSkillStack());
    }

    @Transactional
    public void removePosition(EditParticipantPositionDto editParticipantPositionDto){
        Participant participant = participantRepository.findByMemberIdAndProjectId(editParticipantPositionDto.getMemberId(), editParticipantPositionDto.getProjectId());
        participant.removePosition(editParticipantPositionDto.getPosition());
    }

    @Transactional
    public void removeSkillStack(EditParticipantSkillStackDto editParticipantSkillStackDto) {
        Participant participant = participantRepository.findByMemberIdAndProjectId(editParticipantSkillStackDto.getMemberId(), editParticipantSkillStackDto.getProjectId());
        participant.removeSkillStack(editParticipantSkillStackDto.getSkillStack());

    }


}
