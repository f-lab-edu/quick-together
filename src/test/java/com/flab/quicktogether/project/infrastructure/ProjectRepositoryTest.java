package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;
    private Member member;
    private String projectName = "첫번째 프로젝트";
    private LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime periodDateTime = LocalDateTime.now();
    private MeetingMethod meetingMethod = MeetingMethod.SLACK;
    private String projectSummary = "간단할 설명~";
    private String projectDescription = "긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    @BeforeEach
    void initEach() {
        member = new Member("승재");
        memberRepository.save(member);
    }


    @Test
    @DisplayName("프로젝트 저장 후 조회")
    public void save(){


        Project project = Project.builder()
                .projectName(projectName)
                .founder(member)
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        projectRepository.save(project);

        Optional<Project> findProject = projectRepository.findOne(project.getId());

        Assertions.assertEquals(findProject.get().getId(),project.getId());
        Assertions.assertEquals(findProject.get().getProjectName(),projectName);
        Assertions.assertEquals(findProject.get().getFounder(),member);
        Assertions.assertEquals(findProject.get().getMeetingMethod(), meetingMethod);
        Assertions.assertEquals(findProject.get().getProjectDescriptionInfo().getProjectSummary(), projectSummary);
        Assertions.assertEquals(findProject.get().getProjectDescriptionInfo().getProjectDescription(), projectDescription);


    }

    @Test
    @DisplayName("프로젝트 여러개 저장 후 조회")
    public void saveMany(){

        Project project1 = Project.builder()
                .projectName(projectName)
                .founder(member)
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        Project project2 = Project.builder()
                .projectName("두번째 프로젝트")
                .founder(member)
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();


        projectRepository.save(project1);
        projectRepository.save(project2);

        List<Project> allProject = projectRepository.findAll();
        Assertions.assertEquals(allProject.size(), 2);

    }

    @Test
    @DisplayName("프로젝트 저장시 특정 파라미터 없을 시")
    public void saveParameterNull(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
                    Project.builder()
                            .projectName(projectName)
                            .founder(member)
                            .startDateTime(startDateTime)
                            .periodDateTime(periodDateTime)
                            .meetingMethod(meetingMethod)
                            .projectSummary(projectSummary)
                            .projectDescription(projectDescription)
                            .build();
                }
                );

    }

    @Test
    @DisplayName("프로젝트 저장 후 삭제")
    public void delete(){

        Project project = Project.builder()
                .projectName(projectName)
                .founder(member)
                .startDateTime(startDateTime)
                .periodDateTime(periodDateTime)
                .meetingMethod(meetingMethod)
                .projectSummary(projectSummary)
                .projectDescription(projectDescription)
                .build();

        projectRepository.save(project);

        Project findProject = projectRepository.findOne(project.getId()).get();
        projectRepository.delete(findProject);

        Optional<Project> deletedProject = projectRepository.findOne(findProject.getId());
        Assertions.assertThrows(NoSuchElementException.class, () -> deletedProject.get());

    }

}