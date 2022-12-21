package com.flab.quicktogether.timeplan.domain;

import com.flab.quicktogether.timeplan.domain.exception.AbleRoutineOverlapException;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.timeplan.fixture.TimePlanFixture;
import com.flab.quicktogether.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.flab.quicktogether.timeplan.fixture.TimePlanFixture.*;
import static org.junit.jupiter.api.Assertions.*;

class TimePlanTest {

    @Test
    @DisplayName("AbleRoutines중 겹치는 시간이 존재하면 OverlapEx를 던진다.")
    public void newTimePlan_Overlapped() throws Exception{
        //given
        Member member = TimePlanFixture.TEST_MEMBER;
        List<AbleRoutine> routines = TimePlanFixture.DAY_OVERLAP_ROUTINES;
        List<Event> events = TimePlanFixture.PLANNED_EVENTS;

        //when&then
        assertThrows(AbleRoutineOverlapException.class,()-> new TimePlan(member, routines));


    }

    //만기일에 expiredException이 존재해서 항상 성공하는 테스트를 만들려면 상대적인 시간으로 생각해야하는데 테스트짜기 어렵다.
    @Test
    @DisplayName("절대타임블록으로 변환하면 가능시간에서 이벤트를 제외한 시간을 반환한다.")
    public void extractAbleTimeBlock() throws Exception{
        //given
        Member testMember = TEST_MEMBER;
        List<AbleRoutine> dayEqualRoutines = DAY_EQUAL_ROUTINES;
        List<Event> plannedEvents = PLANNED_EVENTS;

        TimePlan timePlan = new TimePlan(TEST_MEMBER, DAY_EQUAL_ROUTINES);
        List<TimeBlock> expectedAbleTimeBlock = EXPECTED_ABLE_TIME_BLOCK;


        LocalDate fixedDate = FIXED_DATE;
        //when
        List<TimeBlock> result = timePlan.extractAbleTimeBlock(PLANNED_EVENTS, FIXED_DATE, FIXED_DATE);

        //then
        Assertions.assertThat(result).containsExactly(expectedAbleTimeBlock.toArray(new TimeBlock[0]));
    }


    private static Event createPe(LocalDate target, String timeBlockString) {
        String[] split = timeBlockString.split("~");
        String startTimeString = split[0];
        String endTimeString = split[1];
        return new Event(TIME_PLAN,"forwardOverlapEvent",
                new TimeBlock(
                        LocalDateTime.of(target, LocalTime.parse(startTimeString))
                        , LocalDateTime.of(target, LocalTime.parse(endTimeString)))
                );

    }



    public static AbleRoutine getAbleRoutineByRelativeTimeBlock(RegularTimeBlock tb1) {
        return new AbleRoutine(tb1);
    }
}
