package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.application.ParticipantService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.DuplicateInviteMemberException;
import com.flab.quicktogether.project.infrastructure.InviteRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProjectInviteServiceTest {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ProjectInviteService projectInviteService;

    private Member requestMember;
    private Member invitedMember;
    private Member invitedMember2;
    private Project project;

    private String projectName = "첫번째 프로젝트";
    private LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime periodDateTime = LocalDateTime.now();
    private MeetingMethod meetingMethod = MeetingMethod.SLACK;
    private String projectSummary = "간단할 설명~";
    private String projectDescription = "긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    @BeforeEach
    void initEach() {
        requestMember = new Member("홍재");
        invitedMember = new Member("승재");
        invitedMember2 = new Member("승재2");
        memberRepository.save(requestMember);
        memberRepository.save(invitedMember);
        memberRepository.save(invitedMember2);

        Project p = Project.builder()
                .projectName(projectName)
                .founder(requestMember)
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();
        project = p;
        projectRepository.save(project);
        Participant participant = new Participant(requestMember, project, ParticipantRole.ROLE_ADMIN);
        participantRepository.save(participant);
    }

    @Test
    @DisplayName("같은 프로젝트에 중복 초대 되었을 시 DuplicateInviteMemberException 발생한다.")

    void InviteMemberException() {

        //given
        projectInviteService.inviteMember(project.getId(),requestMember.getId(),invitedMember.getId());

        //when
        //then
        Assertions.assertThrows(DuplicateInviteMemberException.class, () -> {
            projectInviteService.inviteMember(project.getId(),requestMember.getId(),invitedMember.getId());
        });

    }

    @Test
    @DisplayName("프로젝트 초대를 거절한 멤버도 다시 초대가 가능하다")
    void rejectInviteMember() {

        //given
        Long invitedMemberId = invitedMember.getId();
        Long projectId = project.getId();
        Long requestMemberId = requestMember.getId();

        projectInviteService.inviteMember(projectId, requestMemberId, invitedMemberId);
        projectInviteService.rejectInvite(projectId,invitedMemberId);

        //when
        projectInviteService.inviteMember(projectId, requestMemberId,invitedMemberId);

        //then
        Assertions.assertEquals(invitedMember.getMemberName(), inviteRepository.findByProjectIdAndInvitedMemberIdWithWait(projectId,invitedMemberId).get().getInvitedMember().getMemberName());

    }

}