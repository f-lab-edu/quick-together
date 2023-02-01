package com.flab.quicktogether.project.application;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
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
    @DisplayName("CreateProjectRequestDto와 멤버를 이용하여 프로젝트를 생성하여 저장한다.")
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

        Optional<Project> findProject = projectRepository.findById(createProjectId);

        Assertions.assertEquals(projectName,findProject.get().getProjectName());


    }

    @Test
    @DisplayName("프로젝트를 삭제 했을 시 상태값이 DELETED로 변한다.")
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
        Project findProject = projectRepository.findById(createProjectId).get();

        projectService.deleteProject(findProject.getId());

        Assertions.assertEquals(projectDeleted,findProject.getProjectStatus());
    }

    @Test
    @DisplayName("프로젝트를 저장하고 저장한 프로젝트를 조회 시 같은 프로젝트이다.")
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

        Project findProject = projectService.retrieveBasicProject(createProjectId);

        Assertions.assertEquals(projectName,findProject.getProjectName());


    }

    @Test
    @DisplayName("없는 프로젝트 조회 시 ProjectNotFoundException 발생한다.")
    void findProjectException() {

        Assertions.assertThrows(ProjectNotFoundException.class,() -> projectService.retrieveBasicProject(1L));


    }

    @Test
    @DisplayName("빈 저장소에 두 개의 프로젝트를 저장하고 전체 프로젝트를 조회했을 시 전체 개수는 2개 늘어난다.")
    void findProjects() {
        List<Project> projects = projectService.retrieveAllBasicProjects();
        int oldSize = projects.size();

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

        projects = projectService.retrieveAllBasicProjects();
        int newSize = projects.size();

        Assertions.assertEquals(oldSize+2,newSize);

    }

    @Test
    @DisplayName("프로젝트의 제목을 수정하면 프로젝트의 제목이 변경된다.")
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

        Project changedProject = projectService.retrieveBasicProject(projectId);

        Assertions.assertEquals(changedProjectName, changedProject.getProjectName());

    }

    @Test
    @DisplayName("프로젝트에 스킬스택을 추가한다.")
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
        Project project = projectRepository.findById(createProjectId).get();
        SkillStack skillStack = SkillStack.JAVA;

        projectService.addSkillStack(project.getId(),new EditProjectSkillStackRequestDto(skillStack));

        Project findProject = projectRepository.findById(project.getId()).get();

        Assertions.assertEquals(findProject.getSkillStacks().get(0), skillStack);
    }

    @Test
    @DisplayName("프로젝트에 이미 가지고 있는 스킬스택을 추가 시 중복 DuplicateProjectSkillStackException 발생한다.")
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
        Project project = projectRepository.findById(createProjectId).get();
        SkillStack skillStack = SkillStack.JAVA;

        projectService.addSkillStack(project.getId(),new EditProjectSkillStackRequestDto(skillStack));

        Assertions.assertThrows(DuplicateProjectSkillStackException.class,() -> projectService.addSkillStack(project.getId(),new EditProjectSkillStackRequestDto(skillStack)));

    }

    @Test
    @DisplayName("프로젝트에 모집 포지션을 추가한다.")
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
        Project project = projectRepository.findById(createProjectId).get();
        Position position = Position.BACKEND;

        projectService.addRecruitmentPosition(project.getId(),new EditRecruitmentPositionsRequestDto(position));

        Project findProject = projectRepository.findById(project.getId()).get();

        Assertions.assertEquals(findProject.getRecruitmentPositions().get(0), position);
    }

    @Test
    @DisplayName("프로젝트에 이미 가지고 있는 모집 포지션을 추가 시 중복 DuplicateProjectPositionException 발생한다.")
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
        Project project = projectRepository.findById(createProjectId).get();
        Position position = Position.BACKEND;

        projectService.addRecruitmentPosition(project.getId(),new EditRecruitmentPositionsRequestDto(position));

        Assertions.assertThrows(DuplicateProjectPositionException.class, () -> projectService.addRecruitmentPosition(project.getId(), new EditRecruitmentPositionsRequestDto(position)));


    }
}