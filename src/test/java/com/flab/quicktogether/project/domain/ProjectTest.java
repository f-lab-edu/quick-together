package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProjectTest {

    @Autowired
    EntityManager em;


    @Test
    @Transactional
    @Rollback(value = false)
    public void createProject(){

        Member member = new Member("승재");
        em.persist(member);

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDate(100L)
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .description("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .founder(member)
                .build();

        em.persist(project);


    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void editProject(){

        Member member = new Member("승재");
        em.persist(member);

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDate(100L)
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .description("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .founder(member)
                .build();

        em.persist(project);

        project.changeProjectName("변경된 프로젝트");

        Project findProject = em.find(Project.class, project.getId());
        System.out.println("findProject.getProjectName() = " + findProject.getProjectName());


    }
}