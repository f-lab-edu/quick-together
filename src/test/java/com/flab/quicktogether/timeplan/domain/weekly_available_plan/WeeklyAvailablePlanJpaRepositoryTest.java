package com.flab.quicktogether.timeplan.domain.weekly_available_plan;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.AvailablePlan;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlanRepository;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import com.flab.quicktogether.timeplan.fixture.AvailablePlanFixture;
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
@Transactional
class WeeklyAvailablePlanJpaRepositoryTest {


    @Autowired
    private final WeeklyAvailablePlanRepository weeklyAvailablePlanRepository;
    @Autowired
    private final MemberRepository memberRepository;

    Member member;
    @Autowired
    public WeeklyAvailablePlanJpaRepositoryTest(MemberRepository memberRepository, WeeklyAvailablePlanRepository weeklyAvailablePlanRepository) {
        this.memberRepository = memberRepository;
        this.weeklyAvailablePlanRepository = weeklyAvailablePlanRepository;
    }

    @BeforeEach
    public void init() {
        member = new Member("testMember");
        memberRepository.save(member);
    }

    @Test
    @DisplayName("AvailablePlan 을 저장하고 찾으면 기존 저장된 AvailablePlan을 반환한다.")
    void save(){
        //given
        Long memberId = memberRepository.findById(member.getId()).get().getId();
        List<RegularTimeBlock> dayEqualRoutines = AvailablePlanFixture.DAY_EQUAL_AVAILABLE_PLAN;

        WeeklyAvailablePlan weeklyAvailablePlan = new WeeklyAvailablePlan(memberId, dayEqualRoutines);

        //when
        weeklyAvailablePlanRepository.save(weeklyAvailablePlan);

        WeeklyAvailablePlan result = weeklyAvailablePlanRepository.findByMemberId(memberId).get();
        //then
        Assertions.assertThat(result).isEqualTo(weeklyAvailablePlan);
    }

}