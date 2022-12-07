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
                .createDateTime(LocalDateTime.now())
                .periodDate(100L)
                .founder(new Founder(member))
                .meetingMethod(MeetingMethod.SLACK)
                .projectDescriptionInfo(new ProjectDescriptionInfo("간단한 설명","긴설명~~~~~~~~~~~~~~~~~~~~~~~~"))
                .build();

        em.persist(project);


    }
}