package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {


    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void createProject() {
    }

    @Test
    void deleteProject() {
    }

    @Test
    void findProject() {
    }

    @Test
    void findProjects() {
    }

    @Test
    void editProject() {
    }
}