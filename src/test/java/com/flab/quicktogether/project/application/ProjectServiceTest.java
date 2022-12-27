package com.flab.quicktogether.project.application;

import com.flab.quicktogether.globalsetting.domain.Position;
import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectSkillStackRequestDto;
import com.flab.quicktogether.project.application.dto.EditRecruitmentPositionsRequestDto;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.domain.ProjectStatus;
import com.flab.quicktogether.project.exception.DuplicateProjectPositionException;
import com.flab.quicktogether.project.exception.DuplicateProjectSkillStackException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.sun.jdi.request.DuplicateRequestException;
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
import java.util.List;
import java.util.Optional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ProjectServiceTest {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectService projectService;


    @BeforeEach
    void initEach() {
        member = new Member("승재");
        memberRepository.save(member);
    }
    private Member member;
    private String projectName = "첫번째 프로젝트";
    private LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime periodDateTime = LocalDateTime.now();
    private MeetingMethod meetingMethod = MeetingMethod.SLACK;
    private String projectSummary = "간단할 설명~";
    private String projectDescription = "긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";



    @Test
    @DisplayName("프로젝트 생성")
    void createProject() {

        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        Long createProjectId = projectService.createProject(createProjectRequestDto);

        Optional<Project> findProject = projectRepository.findOne(createProjectId);

        Assertions.assertEquals(projectName,findProject.get().getProjectName());


    }

    @Test
    @DisplayName("프로젝트 삭제")
    void deleteProject() {
        ProjectStatus projectDeleted = ProjectStatus.DELETED;

        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();
        Long createProjectId = projectService.createProject(createProjectRequestDto);
        Project findProject = projectRepository.findOne(createProjectId).get();

        projectService.deleteProject(findProject.getId());

        Assertions.assertEquals(projectDeleted,findProject.getProjectStatus());
    }

    @Test
    @DisplayName("프로젝트 단일조회")
    void findProject() {
        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();
        Long createProjectId = projectService.createProject(createProjectRequestDto);

        Project findProject = projectService.retrieveProject(createProjectId);

        Assertions.assertEquals(projectName,findProject.getProjectName());


    }

    @Test
    @DisplayName("없는 프로젝트 조회 시 Exception")
    void findProjectException() {

        Assertions.assertThrows(ProjectNotFoundException.class,() -> projectService.retrieveProject(1L));


    }

    @Test
    @DisplayName("프로젝트 전체조회")
    void findProjects() {
        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        projectService.createProject(createProjectRequestDto);
        projectService.createProject(createProjectRequestDto);

        List<Project> projects = projectService.retrieveAllProjects();

        Assertions.assertEquals(projects.size(),2);

    }

    @Test
    @DisplayName("프로젝트 수정")
    void editProject() {
        String changedProjectName = "수정된 프로젝트";
        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();
        Long projectId = projectService.createProject(createProjectRequestDto);

        EditProjectRequestDto editProjectRequestDto = EditProjectRequestDto.builder()
                .projectName(changedProjectName)
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .projectStatus(ProjectStatus.OPEN)
                .build();
        projectService.editProject(projectId, editProjectRequestDto);

        Project changedProject = projectService.retrieveProject(projectId);

        Assertions.assertEquals(changedProjectName, changedProject.getProjectName());

    }

    @Test
    @DisplayName("프로젝트 스킬스택 추가")
    void addProjectSkillStack() {

        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        Long createProjectId = projectService.createProject(createProjectRequestDto);
        Project project = projectRepository.findOne(createProjectId).get();
        SkillStack skillStack = SkillStack.JAVA;

        projectService.addSkillStack(project.getId(),new EditProjectSkillStackRequestDto(skillStack));

        Project findProject = projectRepository.findOne(project.getId()).get();

        Assertions.assertEquals(findProject.getSkillStacks().get(0), skillStack);
    }

    @Test
    @DisplayName("프로젝트 스킬스택 중복 추가 Exception")
    void addProjectSkillStackException() {

        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        Long createProjectId = projectService.createProject(createProjectRequestDto);
        Project project = projectRepository.findOne(createProjectId).get();
        SkillStack skillStack = SkillStack.JAVA;

        projectService.addSkillStack(project.getId(),new EditProjectSkillStackRequestDto(skillStack));

        Assertions.assertThrows(DuplicateProjectSkillStackException.class,() -> projectService.addSkillStack(project.getId(),new EditProjectSkillStackRequestDto(skillStack)));

    }

    @Test
    @DisplayName("프로젝트 모집 포지션 추가")
    void addRecruitmentPosition() {

        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        Long createProjectId = projectService.createProject(createProjectRequestDto);
        Project project = projectRepository.findOne(createProjectId).get();
        Position position = Position.BACKEND;

        projectService.addRecruitmentPosition(project.getId(),new EditRecruitmentPositionsRequestDto(position));

        Project findProject = projectRepository.findOne(project.getId()).get();

        Assertions.assertEquals(findProject.getRecruitmentPositions().get(0), position);
    }

    @Test
    @DisplayName("프로젝트 모집 포지션 중복 추가 Exception")
    void addRecruitmentPositionException() {

        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto.builder()
                .projectName(projectName)
                .memberId(member.getId())
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        Long createProjectId = projectService.createProject(createProjectRequestDto);
        Project project = projectRepository.findOne(createProjectId).get();
        Position position = Position.BACKEND;

        projectService.addRecruitmentPosition(project.getId(),new EditRecruitmentPositionsRequestDto(position));

        Assertions.assertThrows(DuplicateProjectPositionException.class, () -> projectService.addRecruitmentPosition(project.getId(), new EditRecruitmentPositionsRequestDto(position)));


    }
}