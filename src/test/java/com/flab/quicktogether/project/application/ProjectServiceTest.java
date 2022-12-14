package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
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