package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void initEach() {
        Member member = new Member("승재");
        memberRepository.save(member);
    }


    @Test
    @DisplayName("프로젝트 저장 후 조회")
    public void save(){

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        projectRepository.save(project);

        Optional<Project> findProject = projectRepository.findOne(project.getId());

        Assertions.assertEquals(findProject.get().getId(),project.getId());

    }

    @Test
    @DisplayName("프로젝트 여러개 저장 후 조회")
    public void saveMany(){

        Project project1 = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        Project project2 = Project.builder()
                .projectName("두번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
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
                            .projectName("첫번째 프로젝트")
                            .startDateTime(LocalDateTime.now())
                            .periodDateTime(LocalDateTime.now())
                            .meetingMethod(MeetingMethod.SLACK)
                            .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                            .build();
                }
                );

    }

}