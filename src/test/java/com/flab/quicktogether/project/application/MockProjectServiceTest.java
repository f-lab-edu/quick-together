package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectRequestDto;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.domain.ProjectStatus;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.support.like.infrastructure.ProjectLikeRepository;
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
class MockProjectServiceTest {


    Member member;
    CreateProjectRequestDto createProjectRequestDto;

    @InjectMocks
    ProjectService projectService;
    @Mock
    ProjectRepository projectRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    ParticipantRepository participantRepository;

    @Mock
    ProjectLikeRepository projectLikeRepository;

    @BeforeEach
    public void setUp(){
        member = new Member("승재");

         String projectName = "첫번째 프로젝트";
         LocalDateTime startDateTime = LocalDateTime.now();
         LocalDateTime periodDateTime = LocalDateTime.now();
         MeetingMethod meetingMethod = MeetingMethod.SLACK;
         String projectSummary = "간단할 설명~";
         String projectDescription = "긴설명~~~~~~~~~~~";

        createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

    }

    @Test
    @DisplayName("프로젝트 생성 시 프로젝트와 구성원이 저장된다.")
    public void createProject(){

        //given
        Project project = mock(Project.class);
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        given(projectRepository.save(any())).willReturn(Optional.ofNullable(project).get());


        //when
        projectService.createProject(createProjectRequestDto);

        //then
        verify(projectRepository, times(1)).save(any());


    }

    @Test
    @DisplayName("프로젝트 조회 시 프로젝트와 좋아요 조회된다.")
    public void retrieveProject(){

        //given
        Project project = mock(Project.class);
        Long projectId = 1L;

        given(projectRepository.findById(projectId)).willReturn(Optional.ofNullable(project));

        //when
        projectService.retrieveProject(projectId);

        //then
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectLikeRepository, times(1)).countByProjectId(projectId);

    }


    @Test
    @DisplayName("없는 프로젝트를 조회 시 NotFoundException 발생한다.")
    public void retrieveProjectException(){

        //given
        Project project = null;
        Long projectId = 1L;

        given(projectRepository.findById(projectId)).willReturn(Optional.ofNullable(project));

        Assertions.assertThrows(ProjectNotFoundException.class, () -> {
            projectService.retrieveProject(projectId);
        });
    }


    @Test
    @DisplayName("프로젝트 삭제 시 프로젝트의 상태값이 DELETED로 바뀐다.")
    public void deleteProject(){

        //given
        Project project = spy(Project.class);
        ProjectStatus projectStatus = ProjectStatus.DELETED;
        Long projectId = 1L;

        given(projectRepository.findById(projectId)).willReturn(Optional.ofNullable(project));

        //when
        projectService.deleteProject(projectId);


        //then
        Assertions.assertEquals(projectStatus, project.getProjectStatus());

    }

    @Test
    @DisplayName("프로젝트의 제목을 수정 시 제목이 바뀐다.")
    public void editProject(){

        //given
        Project project = spy(Project.class);
        Long projectId = 1L;

        String changeName = "secondProject";
        EditProjectRequestDto editProjectRequestDto = spy(EditProjectRequestDto.class);
        editProjectRequestDto.setProjectName(changeName);

        given(projectRepository.findById(projectId)).willReturn(Optional.ofNullable(project));

        //when
        projectService.editProject(projectId,editProjectRequestDto);

        //then
        Assertions.assertEquals(project.getProjectName(), changeName);

    }

}
