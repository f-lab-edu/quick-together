package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.EntityManager;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ParticipantTest {


    @Autowired
    EntityManager em;


    @Test
    @Transactional
    @Rollback(value = false)
    public void addProjectParticipant() {

        Member member1 = new Member("승재");
        Member member2 = new Member("승재2");
        em.persist(member1);
        em.persist(member2);

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDate(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .description("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        em.persist(project);

        Participant participant = Participant.addMember(member1,project, ParticipantRole.ROLE_ADMIN);
        Participant participant2 = Participant.addMember(member2,project, ParticipantRole.ROLE_USER);

        em.persist(participant);
        em.persist(participant2);

        Participant participants = em.find(Participant.class, participant.getId());

        Assertions.assertEquals(member1, participants.getMember());


    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("프로젝트 1에 참여하고 있는 멤버")
    public void findParticipant() {

        Member member1 = new Member("승재");
        Member member2 = new Member("승재2");
        em.persist(member1);
        em.persist(member2);

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDate(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .description("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        em.persist(project);

        Participant participant = Participant.addMember(member1,project, ParticipantRole.ROLE_ADMIN);
        Participant participant2 = Participant.addMember(member2,project, ParticipantRole.ROLE_USER);

        em.persist(participant);
        em.persist(participant2);

        Participant participants = em.find(Participant.class, participant.getId());

        Assertions.assertEquals(member1, participants.getMember());

        List<Participant> resultList = em.createQuery("select p from Participant p join p.project t where t.id = :projectId", Participant.class)
                .setParameter("projectId", 1)
                .getResultList();

        for (Participant participant1 : resultList) {
            System.out.println("participant1 = " + participant1.getMember().getMemberName());
        }


    }


}