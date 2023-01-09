package com.flab.quicktogether.timeplan.infrastructure;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.timeplan.domain.AbleRoutine;
import com.flab.quicktogether.timeplan.domain.TimePlan;
import com.flab.quicktogether.timeplan.fixture.TimePlanFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
@Transactional
class TimePlanRepositoryTest {


    private final TimePlanRepository timePlanRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public TimePlanRepositoryTest(MemberRepository memberRepository, TimePlanRepository timePlanRepository) {
        this.memberRepository = memberRepository;
        this.timePlanRepository = timePlanRepository;
    }

    @BeforeEach
    public void init() {
        Member member = new Member("testMember");
        memberRepository.save(member);
    }

    @Test
    @DisplayName("timePlan 을 저장하고 찾으면 기존 저장된 timePlan을 반환한다.")
    void save(){
        //given
        Member member = memberRepository.findById(1L).get();
        List<AbleRoutine> dayEqualRoutines = TimePlanFixture.DAY_EQUAL_ROUTINES;
        TimePlan timePlan = new TimePlan(member, dayEqualRoutines);

        //when
        timePlanRepository.save(timePlan);
        timePlanRepository.flush();

        TimePlan result = timePlanRepository.findByMemberId(1L).get();
        //then
        Assertions.assertThat(result).isEqualTo(timePlan);
    }

}