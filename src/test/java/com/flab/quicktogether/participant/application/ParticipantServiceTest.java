package com.flab.quicktogether.participant.application;

import com.flab.quicktogether.globalsetting.domain.Position;
import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.application.dto.EditParticipantPositionRequestDto;
import com.flab.quicktogether.participant.application.dto.EditParticipantSkillStackRequestDto;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.participant.exception.DuplicateParticipantPositionException;
import com.flab.quicktogether.participant.exception.DuplicateParticipantSkillStackException;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.DuplicateProjectParticipationException;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ParticipantServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ParticipantRepository participantRepository;


    private Member member;
    private Member member2;
    private Project project;

    private String projectName = "첫번째 프로젝트";
    private LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime periodDateTime = LocalDateTime.now();
    private MeetingMethod meetingMethod = MeetingMethod.SLACK;
    private String projectSummary = "간단할 설명~";
    private String projectDescription = "긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    @BeforeEach
    void initEach() {
        member = new Member("승재");
        member2 = new Member("승재2");
        memberRepository.save(member);
        memberRepository.save(member2);

        Project p = Project.builder()
                .projectName(projectName)
                .founder(member)
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();
        project = p;
        projectRepository.save(project);

        Participant participant = new Participant(member, project, ParticipantRole.ROLE_ADMIN);
        participantRepository.save(participant);
    }

    @Test
    @DisplayName("프로젝트 구성원 전체 조회")
    void retrieveAllParticipants() {
        participantService.joinProject(project.getId(), member2.getId());

        Assertions.assertEquals(participantService.retrieveAllParticipants(project.getId()).size(),2);
    }

    @Test
    @DisplayName("프로젝트 특정 구성원 조회")
    void retrieveParticipant() {
        Participant participant = participantService.retrieveParticipant(project.getId(), member.getId());

        Assertions.assertEquals(participant.getMember().getId(),member.getId());

    }

    @Test
    @DisplayName("프로젝트 참가")
    void joinProject() {

        Long joinMemberId = member2.getId();
        participantService.joinProject(project.getId(),joinMemberId);

        Participant participant = participantService.retrieveParticipant(project.getId(),joinMemberId);

        Assertions.assertEquals(joinMemberId,participant.getMember().getId());

    }

    @Test
    @DisplayName("프로젝트 중복 참가 Exception")
    void joinProjectException() {

        Long joinMemberId = member2.getId();
        participantService.joinProject(project.getId(),joinMemberId);

        Assertions.assertThrows(DuplicateProjectParticipationException.class, () -> participantService.joinProject(project.getId(), joinMemberId));

    }

    @Test
    @DisplayName("프로젝트 탈퇴")
    void leaveProject() {
        Long projectId = project.getId();
        Long memberId = member.getId();

        participantService.leaveProject(projectId, memberId);

        Assertions.assertThrows(ParticipantNotFoundException.class, () -> participantService.retrieveParticipant(projectId, memberId));

    }

    @Test
    @DisplayName("프로젝트 구성원 권한 변경")
    void changeRole() {
        Long projectId = project.getId();
        Long memberId = member.getId();
        ParticipantRole changeRole = ParticipantRole.ROLE_USER;

        Participant participant = participantService.retrieveParticipant(projectId, memberId);

        participant.changeParticipantRole(changeRole);

        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        Assertions.assertEquals(findParticipant.getParticipantRole(), changeRole);


    }

    @Test
    @DisplayName("프로젝트 구성원 포지션 추가")
    void addPosition() {
        Long projectId = project.getId();
        Long memberId = member.getId();
        Position addPosition = Position.BACKEND;

        participantService.addPosition(projectId,memberId,new EditParticipantPositionRequestDto(addPosition));

        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        Assertions.assertEquals(findParticipant.getPositions().get(0), addPosition);

    }

    @Test
    @DisplayName("프로젝트 구성원 중복 포지션 추가 Exception")
    void addPositionException() {
        Long projectId = project.getId();
        Long memberId = member.getId();
        Position addPosition = Position.BACKEND;

        participantService.addPosition(projectId,memberId,new EditParticipantPositionRequestDto(addPosition));

        Assertions.assertThrows(DuplicateParticipantPositionException.class, () -> participantService.addPosition(projectId,memberId,new EditParticipantPositionRequestDto(addPosition)));

    }

    @Test
    @DisplayName("프로젝트 구성원 포지션 삭제")
    void removePosition() {
        Long projectId = project.getId();
        Long memberId = member.getId();
        Position position = Position.BACKEND;

        Participant participant = participantService.retrieveParticipant(projectId, memberId);
        participant.addPosition(position);

        participantService.removePosition(projectId,memberId,new EditParticipantPositionRequestDto(position));

        Assertions.assertEquals(participant.getPositions().size(),0);

    }

    @Test
    @DisplayName("프로젝트 구성원 스킬스택 추가")
    void addSkillStack() {
        Long projectId = project.getId();
        Long memberId = member.getId();
        SkillStack addSkillStack = SkillStack.JAVA;

        participantService.addSkillStack(projectId,memberId,new EditParticipantSkillStackRequestDto(addSkillStack));

        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        Assertions.assertEquals(findParticipant.getSkillStacks().get(0), addSkillStack);
    }

    @Test
    @DisplayName("프로젝트 구성원 스킬스택 중복 추가 Exception")
    void addSkillStackException() {
        Long projectId = project.getId();
        Long memberId = member.getId();
        SkillStack addSkillStack = SkillStack.JAVA;

        participantService.addSkillStack(projectId,memberId,new EditParticipantSkillStackRequestDto(addSkillStack));

        Assertions.assertThrows(DuplicateParticipantSkillStackException.class, () -> participantService.addSkillStack(projectId,memberId,new EditParticipantSkillStackRequestDto(addSkillStack)));
    }

    @Test
    @DisplayName("프로젝트 구성원 스킬스택 삭제")
    void removeSkillStack() {
        Long projectId = project.getId();
        Long memberId = member.getId();
        SkillStack skillStack = SkillStack.JAVA;

        Participant participant = participantService.retrieveParticipant(projectId, memberId);
        participant.addSkillStack(skillStack);

        participantService.removeSkillStack(projectId,memberId,new EditParticipantSkillStackRequestDto(skillStack));

        Assertions.assertEquals(participant.getSkillStacks().size(),0);
    }
}