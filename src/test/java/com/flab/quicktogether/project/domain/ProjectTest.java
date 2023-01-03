package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.timeplan.domain.etc.MinuteUnit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProjectTest {

    @PersistenceContext
    EntityManager em;


    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("프로젝트 생성, 빌더패턴")
    public void createProject() {

        Member member = new Member("승재");
        em.persist(member);

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .meetingTimeUnit(MinuteUnit.TEN)
                .build();

        em.persist(project);


    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("프로젝트 생성, 생성자")
    public void createProject2() {

        Member member = new Member("승재");
        em.persist(member);

        Project project = Project.createProject("첫번째 프로젝트",member,"간단할 설명~","긴설명~~~~~~~~~~~~~~~~~~~~~~~~~",MeetingMethod.SLACK,LocalDateTime.now(),LocalDateTime.now());

        em.persist(project);

        Participant participant = new Participant(member, project, ParticipantRole.ROLE_ADMIN);
        em.persist(participant);


    }


    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("프로젝트 이름 변경")
    public void changeProjectName() {

        Member member = new Member("승재");
        em.persist(member);

        String editProjectName = "변경된 프로젝트";

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .meetingTimeUnit(MinuteUnit.TEN)
                .build();

        em.persist(project);

        project.changeProjectName("변경된 프로젝트");

        Project findProject = em.find(Project.class, project.getId());
        Assertions.assertEquals(editProjectName, findProject.getProjectName());
        System.out.println("findProject.getProjectName() = " + findProject.getProjectName());


    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void changeProjectSummary() {

        Member member = new Member("승재");
        em.persist(member);

        String editProjectSummary = "간단한 설명 변경";

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .meetingTimeUnit(MinuteUnit.TEN)

                .build();

        em.persist(project);

        project.changeProjectSummary(editProjectSummary);


        Project findProject = em.find(Project.class, project.getId());

        Assertions.assertEquals(editProjectSummary, findProject.getProjectDescriptionInfo().getProjectSummary());
        System.out.println("findProject.getProjectName() = " + findProject.getProjectDescriptionInfo().getProjectSummary());

    }


}