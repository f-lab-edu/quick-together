package com.flab.quicktogether.timeplan.domain;

import com.flab.quicktogether.timeplan.domain.exception.AbleRoutineOverlapException;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.fixture.TimePlanFixture;
import com.flab.quicktogether.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.flab.quicktogether.timeplan.fixture.TimePlanFixture.*;
import static org.junit.jupiter.api.Assertions.*;

class TimePlanTest {

    @Test
    @DisplayName("AbleRoutines중 겹치는 시간이 존재하는 경우 TimePlan이 생성되면 OverlapEx를 던진다.")
    public void newTimePlan_Overlapped() throws Exception{
        //given
        Member member = TimePlanFixture.TEST_MEMBER;
        List<AbleRoutine> routines = TimePlanFixture.DAY_OVERLAP_ROUTINES;

        //when&then
        assertThrows(AbleRoutineOverlapException.class,()-> new TimePlan(member, routines, null));

    }

    @Test
    @DisplayName("절대타임블록으로 변환하면 가능시간에서 이벤트를 제외한 시간을 반환한다.")
    public void extractAbleTimeBlock() throws Exception{
        //given
        Member testMember = TEST_MEMBER;
        List<AbleRoutine> dayEqualRoutines = DAY_EQUAL_ROUTINES;
        List<Event> plannedEvents = PLANNED_EVENTS;

        TimePlan timePlan = new TimePlan(TEST_MEMBER, DAY_EQUAL_ROUTINES,null);

        List<TimeBlock> expectedAbleTimeBlock = EXPECTED_ABLE_TIME_BLOCK;


        LocalDate fixedDate = FIXED_DATE;
        //when
        List<TimeBlock> result = timePlan.extractAbleTimeBlock(FIXED_DATE, FIXED_DATE);

        //then
        Assertions.assertThat(result).containsExactly(expectedAbleTimeBlock.toArray(new TimeBlock[0]));
    }

}
