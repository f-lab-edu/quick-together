package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.ParticipantRole;
import com.flab.quicktogether.project.domain.Project;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class ParticipantRepositoryTest {


    @Autowired
    EntityManager em;

    @Autowired
    ParticipantRepository participantRepository;


    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원이 참여하고 있는 프로젝트 정보들")
    public void findParticipants() {

        Member member1 = new Member("승재");
        Member member2 = new Member("승재2");
        em.persist(member1);
        em.persist(member2);

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .description("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        em.persist(project);

        Project project2 = Project.builder()
                .projectName("두번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .description("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        em.persist(project2);

        Participant participant = Participant.addMember(project,member1);
        Participant participant2 = Participant.addMember(project2,member1);

        em.persist(participant);
        em.persist(participant2);

        List<Participant> findParticipant = participantRepository.findParticipantsByMemberId(member1.getId());

        for (Participant participant1 : findParticipant) {
            System.out.println("participant1 = " + participant1.getProject().getProjectName());

        }


    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("특정 회원의 특정 프로젝트")
    public void findParticipant() {

        Member member1 = new Member("승재");
        Member member2 = new Member("승재2");
        em.persist(member1);
        em.persist(member2);

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .description("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        em.persist(project);

        Project project2 = Project.builder()
                .projectName("두번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .description("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        em.persist(project2);

        Participant participant = Participant.addMember(project,member1);
        Participant participant2 = Participant.addMember(project2,member1);

        em.persist(participant);
        em.persist(participant2);

        Optional<Participant> byMemberIdAndProjectId1 = participantRepository.findByMemberIdAndProjectId(project.getId(), member1.getId());

            System.out.println("participant1 = " + byMemberIdAndProjectId1.get().getProject().getProjectName());


    }

}