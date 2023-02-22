package com.flab.quicktogether.timeplan.domain.weekly_available_plan;

import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import com.flab.quicktogether.timeplan.domain.exception.AvailablePlanOverlapException;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.fixture.AvailablePlanFixture;
import com.flab.quicktogether.timeplan.fixture.TimeBlockFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.flab.quicktogether.timeplan.fixture.AvailablePlanFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class WeeklyAvailablePlanTest {

    @Test
    @DisplayName("AvailablePlan 겹치는 시간이 존재하는 경우 TimePlan이 생성되면 OverlapEx를 던진다.")
    public void newWeeklyAvailableRoutine_overlapped_throwOverlapException() throws Exception{
        //given
        Long testMemberId = TEST_MEMBER_ID;
        List<RegularTimeBlock> routines = AvailablePlanFixture.DAY_OVERLAP_ROUTINES;

        Collections.sort(routines);

        List<DayOfWeek> week = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.FRIDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        List<DayOfWeek> collect = week.stream().collect(Collectors.toList());
        Collections.sort(collect);

        //when&then
        assertThrows(AvailablePlanOverlapException.class,()-> new WeeklyAvailablePlan(testMemberId, routines));
    }

    @Test
    @DisplayName("겹치지 않는 AvailablePlan과 단일기간을 갖춘 AvailableSchedule을 추출할 경우 동일개수의 TimeBlock이 반환된다.")
    void extractAvailableSchedule() throws Exception{
        //given
        Long testMemberId = TEST_MEMBER_ID;
        List<RegularTimeBlock> routines = AvailablePlanFixture.getEqualTimeWeeklyAvailablePlan(
                "10:00~12:00",
                        "13:00~18:00",
                         "20:00~00:00");
        LocalDate startDate = FIXED_DATE;
        LocalDate endDate = startDate;

        Range singleDateRange = Range.asCommonTime(startDate, endDate, UTC);
        WeeklyAvailablePlan weeklyAvailablePlan = new WeeklyAvailablePlan(testMemberId, routines);

        TimeBlock expected1 = TimeBlockFixture.newTimeBlockFixture(FIXED_DATE, "10:00~12:00");
        TimeBlock expected2 = TimeBlockFixture.newTimeBlockFixture(FIXED_DATE, "13:00~18:00");
        TimeBlock expected3 = TimeBlockFixture.newTimeBlockFixture(FIXED_DATE, "20:00~00:00");
        int expectedCount = 3;
        //when
        List<TimeBlock> result = weeklyAvailablePlan.extractAvailableTimeSchedule(singleDateRange);

        //then
        assertThat(result).hasSize(expectedCount);
        assertThat(result).containsExactly(expected1, expected2, expected3);
    }

    @Test
    @DisplayName("단일기간을 갖고, 해당시간의 종료시간과 다음시간의 시작시간이 같은 경우 AvailableSchedule추출시 두 블럭을 합친 값을 반환한다.")
    void extractAvailableSchedule_glue() throws Exception{
        //given
        Long testMemberId = TEST_MEMBER_ID;
        List<RegularTimeBlock> routines = AvailablePlanFixture.getEqualTimeWeeklyAvailablePlan(
                "10:00~12:00",
                        "12:00~14:00",
                         "20:00~00:00");
        LocalDate startDate = FIXED_DATE;
        LocalDate endDate = startDate;

        Range singleDateRange = Range.asCommonTime(startDate, endDate, UTC);
        WeeklyAvailablePlan weeklyAvailablePlan = new WeeklyAvailablePlan(testMemberId, routines);

        TimeBlock expected1 = TimeBlockFixture.newTimeBlockFixture(FIXED_DATE, "10:00~14:00");
        TimeBlock expected2 = TimeBlockFixture.newTimeBlockFixture(FIXED_DATE, "20:00~00:00");
        int expectedCount = 2;
        //when
        List<TimeBlock> result = weeklyAvailablePlan.extractAvailableTimeSchedule(singleDateRange);

        //then
        assertThat(result).hasSize(expectedCount);
        assertThat(result).containsExactly(expected1, expected2);
    }

    @Test
    @DisplayName("여러 기간동안의 AvailableSchedule을 추출할 경우 모든 기간의 값을 포함한다.")
    void extractAvailableSchedule_pluralRange() throws Exception{
        //given
        Long testMemberId = TEST_MEMBER_ID;
        List<RegularTimeBlock> routines = AvailablePlanFixture.getEqualTimeWeeklyAvailablePlan(
                "10:00~12:00",
                        "12:00~14:00",
                         "20:00~00:00");
        LocalDate startDate = FIXED_DATE;
        LocalDate endDate = startDate.plusWeeks(1L);
        LocalDate endPoint = endDate.plusDays(1L);

        Range singleDateRange = Range.asCommonTime(startDate, endDate, UTC);
        WeeklyAvailablePlan weeklyAvailablePlan = new WeeklyAvailablePlan(testMemberId, routines);

        List<TimeBlock> expectedList = startDate.datesUntil(endPoint)
                .flatMap(date ->
                        List.of(TimeBlockFixture.newTimeBlockFixture(date, "10:00~14:00"),
                                TimeBlockFixture.newTimeBlockFixture(date, "20:00~00:00"))
                                .stream())
                .toList();

        int expectedCount = expectedList.size();
        //when
        List<TimeBlock> result = weeklyAvailablePlan.extractAvailableTimeSchedule(singleDateRange);

        //then
        assertThat(result).hasSize(expectedCount);
        assertThat(result).containsExactly(expectedList.toArray(new TimeBlock[0]));
    }

}
