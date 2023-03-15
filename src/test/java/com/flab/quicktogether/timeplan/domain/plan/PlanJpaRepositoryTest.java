package com.flab.quicktogether.timeplan.domain.plan;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.fixture.ProjectFixture;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.flab.quicktogether.project.fixture.ProjectFixture.FOUNDER;
import static com.flab.quicktogether.timeplan.fixture.PlanFixture.*;
import static com.flab.quicktogether.timeplan.fixture.PlanFixture.FIXED_DATE;
import static com.flab.quicktogether.timeplan.fixture.PlanFixture.OUT_DATE;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class PlanJpaRepositoryTest {

    @Autowired
    PlanJpaRepository planJpaRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    ProjectRepository projectRepository;

    //fixture
    Member testMember1 = new Member("testMember");
    Member founder = FOUNDER;
    Project projectByFounder = ProjectFixture.NORMAL_FIXTURE1;
    Participant participant = new Participant(testMember1, projectByFounder, ParticipantRole.ROLE_ADMIN);

    Plan outDatePlan1;
    Plan outDatePlan2;
    Plan legalPlan1;
    Plan legalPlan2;
    Plan overLimitPlan1;
    @BeforeEach
    @Rollback(false)
    public void beforeEach() throws Exception {

        //Member 저장
        memberRepository.save(testMember1);
        memberRepository.save(founder);

        //Member Id 반환
        Long testMemberId1 = memberRepository.findById(testMember1.getId()).get().getId();

        //Project 저장
        projectRepository.save(projectByFounder);

        //Participant 저장

        participantRepository.save(participant);

        //Plan 저장
        outDatePlan1 = newPlanIgnoreEx(testMemberId1, randomName(), OUT_DATE, "10:00~15:00");
        outDatePlan2 = newPlanIgnoreEx(testMemberId1, randomName(), OUT_DATE, "18:00~20:00");
        legalPlan1 = newPlanIgnoreEx(testMemberId1, randomName(), FIXED_DATE, "10:00~15:00");
        legalPlan2 = newPlanIgnoreEx(testMemberId1, randomName(), FIXED_DATE, "18:00~20:00");
        overLimitPlan1 = newPlanIgnoreEx(testMemberId1, randomName(), OVER_LIMIT_DATE, "18:00~20:00");

        planJpaRepository.save(outDatePlan1);
        planJpaRepository.save(outDatePlan2);
        planJpaRepository.save(legalPlan1);
        planJpaRepository.save(legalPlan2);
        planJpaRepository.save(overLimitPlan1);

        memberRepository.flush();
        projectRepository.flush();
        participantRepository.flush();
        planJpaRepository.flush();

    }

    @Test
    void findByProjectIdAndAfterCurrent() {
        //given
        Long projectId = projectRepository.findByFounder(FOUNDER).get().getId();

        //when
        List<Plan> result = planJpaRepository.findByProjectId(projectId, LIMIT_DATE);

        //then
        assertThat(result).hasSize(2);


    }

}