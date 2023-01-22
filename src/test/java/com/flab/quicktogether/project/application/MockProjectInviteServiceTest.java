package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.domain.Participants;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.support.invite.exception.DuplicateInviteMemberException;
import com.flab.quicktogether.project.support.invite.infrastructure.InviteRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.support.invite.application.ProjectInviteService;
import com.flab.quicktogether.project.support.invite.domain.Invite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    InviteRepository inviteRepository;



    @BeforeEach
    public void setUp(){


    }

    @Test
    @DisplayName("프로젝트에 멤버를 초대할 경우 권한 검사와 저장을 한다.")
    public void inviteMember(){

        //given
        Project project = mock(Project.class);
        Member requestMember = mock(Member.class);
        Member invitedMember = mock(Member.class);
        Participants participants = mock(Participants.class);
        long requestMemberId = 1;
        long invitedMemberId = 2;

        given(projectRepository.findById(project.getId())).willReturn(Optional.ofNullable(project));
        given(project.getParticipants()).willReturn(participants);
        given(memberRepository.findById(requestMemberId)).willReturn(Optional.ofNullable(requestMember));
        given(memberRepository.findById(invitedMemberId)).willReturn(Optional.ofNullable(invitedMember));

        //when
        projectInviteService.inviteMember(project.getId(),requestMemberId,invitedMemberId);

        //then
        verify(inviteRepository, times(1)).save(any());
        verify(participants,times(1)).isAdmin(requestMemberId);
        verify(participants,times(1)).isParticipantNot(invitedMemberId);

    }

    @Test
    @DisplayName("같은 프로젝트에 똑같은 멤버를 두 번 초대할 경우 중복 Exception이 발생한다.")
    public void inviteMemberException(){

        //given
        Project project = mock(Project.class);
        Participants participants = mock(Participants.class);
        Invite invite = mock(Invite.class);
        long requestMemberId = 1;
        long invitedMemberId = 2;

        given(projectRepository.findById(project.getId())).willReturn(Optional.ofNullable(project));
        given(project.getParticipants()).willReturn(participants);
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
        Project project = mock(Project.class);
        Member invitedMember = mock(Member.class);
        Invite invite = spy(Invite.class);

        given(inviteRepository.findByProjectIdAndInvitedMemberIdWithWait(project.getId(), invitedMember.getId())).willReturn(Optional.ofNullable(invite));
        given(projectRepository.findById(project.getId())).willReturn(Optional.ofNullable(project));
        given(memberRepository.findById(invitedMember.getId())).willReturn(Optional.ofNullable(invitedMember));
        doNothing().when(invite).accept();

        //when
        projectInviteService.acceptInvite(project.getId(),invitedMember.getId());

        verify(invite,times(1)).accept();

    }

}
