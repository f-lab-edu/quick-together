package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.application.ParticipantService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectRequestDto;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.exception.DuplicateInviteMemberException;
import com.flab.quicktogether.project.exception.ProjectNoLikeException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.InviteRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockProjectInviteServiceTest {
    @InjectMocks
    ProjectInviteService projectInviteService;

    @Mock
    ProjectRepository projectRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    ParticipantRepository participantRepository;
    @Mock
    InviteRepository inviteRepository;
    @Mock
    ParticipantService participantService;


    @BeforeEach
    public void setUp(){


    }

    @Test
    @DisplayName("프로젝트에 멤버를 초대할 경우 권한 검사와 저장을 한다.")
    public void inviteMember(){

        //given
        Project project = spy(Project.class);
        Member requestMember = spy(Member.class);
        Member invitedMember = spy(Member.class);
        Participant participant = spy(Participant.class);
        participant.changeParticipantRole(ParticipantRole.ROLE_ADMIN);
        long requestMemberId = 1;
        long invitedMemberId = 2;

        given(memberRepository.findById(requestMemberId)).willReturn(Optional.ofNullable(requestMember));
        given(memberRepository.findById(invitedMemberId)).willReturn(Optional.ofNullable(invitedMember));
        given(projectRepository.findById(project.getId())).willReturn(Optional.ofNullable(project));
        given(participantRepository.findByProjectIdAndMemberId(project.getId(), requestMemberId)).willReturn(Optional.ofNullable(participant));
        given(participantRepository.findByProjectIdAndMemberId(project.getId(), invitedMemberId)).willReturn(Optional.ofNullable(null));


        //when
        projectInviteService.inviteMember(project.getId(),requestMemberId,invitedMemberId);

        //then
        verify(inviteRepository, times(1)).save(any());
        verify(participant,times(1)).checkPermission();

    }

    @Test
    @DisplayName("같은 프로젝트에 똑같은 멤버를 두 번 초대할 경우 중복 Exception이 발생한다.")
    public void inviteMemberException(){

        //given
        Project project = spy(Project.class);
        Member requestMember = spy(Member.class);
        Member invitedMember = spy(Member.class);
        Participant participant = spy(Participant.class);
        participant.changeParticipantRole(ParticipantRole.ROLE_ADMIN);
        Invite invite = mock(Invite.class);
        long requestMemberId = 1;
        long invitedMemberId = 2;

        given(memberRepository.findById(requestMemberId)).willReturn(Optional.ofNullable(requestMember));
        given(memberRepository.findById(invitedMemberId)).willReturn(Optional.ofNullable(invitedMember));
        given(projectRepository.findById(project.getId())).willReturn(Optional.ofNullable(project));
        given(participantRepository.findByProjectIdAndMemberId(project.getId(), requestMemberId)).willReturn(Optional.ofNullable(participant));
        given(participantRepository.findByProjectIdAndMemberId(project.getId(), invitedMemberId)).willReturn(Optional.ofNullable(null));
        given(inviteRepository.findByProjectIdAndInvitedMemberIdWithWait(project.getId(), invitedMemberId)).willReturn(Optional.ofNullable(invite));


        //then
        Assertions.assertThrows(DuplicateInviteMemberException.class, () -> {
            projectInviteService.inviteMember(project.getId(),requestMemberId,invitedMemberId);
        });

    }

    @Test
    @DisplayName("멤버가 프로젝트 초대에 수락한다.")
    public void acceptInvite(){

        //given
        Project project = spy(Project.class);
        Member invitedMember = spy(Member.class);
        Invite invite = spy(Invite.class);

        given(inviteRepository.findByProjectIdAndInvitedMemberIdWithWait(project.getId(), invitedMember.getId())).willReturn(Optional.ofNullable(invite));

        //when
        projectInviteService.acceptInvite(project.getId(),invitedMember.getId());

        //then
        verify(inviteRepository, times(1)).findByProjectIdAndInvitedMemberIdWithWait(project.getId(), invitedMember.getId());
        verify(participantService,times(1)).joinProject(project.getId(),invitedMember.getId());
        Assertions.assertEquals(invite.getInviteStatus(), InviteStatus.ACCEPT);

    }

}
