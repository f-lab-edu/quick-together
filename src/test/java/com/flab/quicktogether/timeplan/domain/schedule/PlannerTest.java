package com.flab.quicktogether.timeplan.domain.schedule;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.flab.quicktogether.timeplan.domain.exception.NotMatchableTimeException;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.planner.Planner;
import com.flab.quicktogether.timeplan.domain.planner.RoughlyPlan;
import com.flab.quicktogether.timeplan.domain.setting.MinuteUnit;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSetting;
import com.flab.quicktogether.timeplan.domain.setting.SequencePriority;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.weekly_available_plan.WeeklyAvailablePlan;
import com.flab.quicktogether.timeplan.fixture.PlanFixture;
import com.flab.quicktogether.timeplan.fixture.RegularTimeBlockFixture;
import com.flab.quicktogether.timeplan.fixture.TimeBlockFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.flab.quicktogether.timeplan.fixture.AvailablePlanFixture.FIXED_DATE;
import static org.assertj.core.api.Assertions.*;

class PlannerTest {
    private final Planner planner = new Planner();

    @Test
    @DisplayName("겹치지 않는 두 WeeklyPlan을 두고 시간을 제안하면 NotFoundSatisfiedAllException을 던진다.")
    void suggestTime_seperated2Blocks_throwNotFoundSatisfiedAllException() throws Exception{
        /**
         * given -> 19:00~21:00, 21:00~22:00, minuteUnit = TEN, Duration = 30
         * return -> NotFoundSatisfiedAllException
         */

        //given
        Range range = Range.asCommonTime(FIXED_DATE, FIXED_DATE);
        RoughlyPlan roughlyPlan = new RoughlyPlan(range, 30, MinuteUnit.TEN);

        PlannerSetting plannerSetting = PlannerSetting.builder()
                .minuteUnit(MinuteUnit.TEN)
                .limit(10)
                .build();

        List<RegularTimeBlock> block1 = RegularTimeBlockFixture.create(FIXED_DATE, "19:00~21:00");
        List<RegularTimeBlock> block2 = RegularTimeBlockFixture.create(FIXED_DATE, "21:00~22:00");

        WeeklyAvailablePlan weeklyPlan1 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block1);
        WeeklyAvailablePlan weeklyPlan2 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block2);

        List<WeeklyAvailablePlan> weeklyPlans = List.of(weeklyPlan1, weeklyPlan2);
        //when
        Exception result = catchException(() -> planner.suggestTime(roughlyPlan, weeklyPlans, null, plannerSetting));

        //then
        assertThat(result).isInstanceOf(NotMatchableTimeException.class);
    }

    @Test
    @DisplayName("두 타임블록을 두고 시간을 제안하면 모두 공통적으로 가능한 시간에 배정된다.")
    void suggestTime_2Block() {
        /**
         * given -> 19:00~21:00, 20:00~22:00, minuteUnit = TEN, Duration = 30
         * return -> 20:00~20:30, 20:10~20:40, 20:20~20:50, 20:30~21:00
         */
        //given
        Range range = Range.asCommonTime(FIXED_DATE, FIXED_DATE);
        RoughlyPlan roughlyPlan = new RoughlyPlan(range, 30, MinuteUnit.TEN);

        PlannerSetting plannerSetting = PlannerSetting.builder()
                .minuteUnit(MinuteUnit.TEN)
                .limit(10)
                .build();

        List<RegularTimeBlock> block1 = RegularTimeBlockFixture.create(FIXED_DATE, "19:00~21:00");
        List<RegularTimeBlock> block2 = RegularTimeBlockFixture.create(FIXED_DATE, "20:00~22:00");

        WeeklyAvailablePlan weeklyPlan1 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block1);
        WeeklyAvailablePlan weeklyPlan2 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block2);
        List<TimeBlock> expected = TimeBlockFixture.createTimeBlocks(FIXED_DATE,
                "20:00~20:30",
                "20:10~20:40",
                "20:20~20:50",
                "20:30~21:00");
        List<WeeklyAvailablePlan> weeklyPlans = List.of(weeklyPlan1, weeklyPlan2);
        //when
        List<TimeBlock> result = planner.suggestTime(roughlyPlan, weeklyPlans, null, plannerSetting);

        //then
        assertThat(result).containsExactly(expected.toArray(new TimeBlock[0]));
    }

    @Test
    @DisplayName("세 타임블록을 두고 시간을 제안하면 모두 공통적으로 가능한 시간에 배정된다. ")
    void suggestTime_3Block() throws Exception{

        /**
         * given -> 19:00~21:00, 20:00~22:00, 20:30~21:30, minuteUnit = TEN, Duration = 30
         * return -> 20:30~21:00
         */
        //given
        Range range = Range.asCommonTime(FIXED_DATE, FIXED_DATE);
        RoughlyPlan roughlyPlan = new RoughlyPlan(range, 30, MinuteUnit.TEN);

        PlannerSetting plannerSetting = PlannerSetting.builder()
                .minuteUnit(MinuteUnit.TEN)
                .limit(10)
                .build();

        List<RegularTimeBlock> block1 = RegularTimeBlockFixture.create(FIXED_DATE, "19:00~21:00");
        List<RegularTimeBlock> block2 = RegularTimeBlockFixture.create(FIXED_DATE, "20:00~22:00");
        List<RegularTimeBlock> block3 = RegularTimeBlockFixture.create(FIXED_DATE, "20:30~21:30");

        WeeklyAvailablePlan weeklyPlan1 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block1);
        WeeklyAvailablePlan weeklyPlan2 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block2);
        WeeklyAvailablePlan weeklyPlan3 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block3);

        List<TimeBlock> expected = TimeBlockFixture.createTimeBlocks(FIXED_DATE, "20:30~21:00");
        List<WeeklyAvailablePlan> weeklyPlans = List.of(weeklyPlan1, weeklyPlan2, weeklyPlan3);

        //when
        List<TimeBlock> result = planner.suggestTime(roughlyPlan, weeklyPlans, null, plannerSetting);

        //then
        assertThat(result).containsExactly(expected.toArray(new TimeBlock[0]));
    }

    @Test
    @DisplayName("세 타임블록중 각각 겹치는 블록을 갖지만, 모두 공통되는 블록이 없을 경우 NotFoundSatisfiedAllException을 반환한다.")
    void suggestTime_eachOverlapButNotSatisfiedAll_throwNotFoundSatisfiedAllException() throws Exception{
        /**
         * given -> 19:00~21:00, 20:00~22:00, 21:00~00:00, minuteUnit = TEN, Duration = 30
         * return -> NotFoundSatisfiedAllException
         */
        //given
        Range range = Range.asCommonTime(FIXED_DATE, FIXED_DATE);
        RoughlyPlan roughlyPlan = new RoughlyPlan(range, 30, MinuteUnit.TEN);

        PlannerSetting plannerSetting = PlannerSetting.builder()
                .minuteUnit(MinuteUnit.TEN)
                .build();

        List<RegularTimeBlock> block1 = RegularTimeBlockFixture.create(FIXED_DATE, "19:00~21:00");
        List<RegularTimeBlock> block2 = RegularTimeBlockFixture.create(FIXED_DATE, "20:00~22:00");
        List<RegularTimeBlock> block3 = RegularTimeBlockFixture.create(FIXED_DATE, "21:00~00:00");

        WeeklyAvailablePlan weeklyPlan1 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block1);
        WeeklyAvailablePlan weeklyPlan2 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block2);
        WeeklyAvailablePlan weeklyPlan3 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block3);

        List<WeeklyAvailablePlan> weeklyPlans = List.of(weeklyPlan1, weeklyPlan2, weeklyPlan3);

        //when
        Exception result = catchException(() -> planner.suggestTime(roughlyPlan, weeklyPlans, null, plannerSetting));
        //then
        assertThat(result).isInstanceOf(NotMatchableTimeException.class);
    }

    @Test
    @DisplayName("두 타임블록을 두고 하나의 겹쳐지는 Plan이 존재할때 시간을 제안하면 Plan을 제외한 시간이 반환된다.")
    void suggestTime_2BlockAnd1PlanOverLap() throws Exception{
        /**
         * given -> Available(19:00~21:00, 20:00~22:00), Plan(20:40~00:00), minuteUnit = TEN, Duration = 30,
         * return -> 20:00~20:30, 20:10~20:40
         */
        //given
        Range range = Range.asCommonTime(FIXED_DATE, FIXED_DATE);
        RoughlyPlan roughlyPlan = new RoughlyPlan(range, 30, MinuteUnit.TEN);

        PlannerSetting plannerSetting = PlannerSetting.builder()
                .minuteUnit(MinuteUnit.TEN)
                .limit(10)
                .build();

        List<RegularTimeBlock> block1 = RegularTimeBlockFixture.create(FIXED_DATE, "19:00~21:00");
        List<RegularTimeBlock> block2 = RegularTimeBlockFixture.create(FIXED_DATE, "20:00~22:00");

        Plan plan = PlanFixture.newPlanIgnoreEx((long) RandomUtil.getPositiveInt(), UUID.randomUUID().toString(), FIXED_DATE, "20:40~00:00");

        WeeklyAvailablePlan weeklyPlan1 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block1);
        WeeklyAvailablePlan weeklyPlan2 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block2);
        List<TimeBlock> expected = TimeBlockFixture.createTimeBlocks(FIXED_DATE,
                "20:00~20:30",
                "20:10~20:40");
        List<WeeklyAvailablePlan> weeklyPlans = List.of(weeklyPlan1, weeklyPlan2);
        //when
        List<TimeBlock> result = planner.suggestTime(roughlyPlan, weeklyPlans, List.of(plan), plannerSetting);

        //then
        assertThat(result).containsExactly(expected.toArray(new TimeBlock[0]));
    }

    @Test
    @DisplayName("필요여분시간이 존재하는 경우 시간을 제안하면 기존 스케줄과 여분시간만큼 떨어진 구간만 반환된다.")
    void suggestTime_2BlockAnd1PlanWithMarginalTime() throws Exception{
        /**
         * given -> 19:00~21:00, 20:00~22:00, minuteUnit = TEN, duration = 30, marginalMinute = 10
         * return -> 20:10~20:40, 20:20~20:50
         */
        //given
        Range range = Range.asCommonTime(FIXED_DATE, FIXED_DATE);
        RoughlyPlan roughlyPlan = new RoughlyPlan(range, 30, MinuteUnit.TEN);

        PlannerSetting plannerSetting = PlannerSetting.builder()
                .minuteUnit(MinuteUnit.TEN)
                .marginalMinutes(10)
                .limit(10)
                .build();

        List<RegularTimeBlock> block1 = RegularTimeBlockFixture.create(FIXED_DATE, "19:00~21:00");
        List<RegularTimeBlock> block2 = RegularTimeBlockFixture.create(FIXED_DATE, "20:00~22:00");

        WeeklyAvailablePlan weeklyPlan1 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block1);
        WeeklyAvailablePlan weeklyPlan2 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block2);
        List<TimeBlock> expected = TimeBlockFixture.createTimeBlocks(FIXED_DATE,
                "20:10~20:40",
                "20:20~20:50");

        List<WeeklyAvailablePlan> weeklyPlans = List.of(weeklyPlan1, weeklyPlan2);
        //when
        List<TimeBlock> result = planner.suggestTime(roughlyPlan, weeklyPlans, null, plannerSetting);

        //then
        assertThat(result).containsExactly(expected.toArray(new TimeBlock[0]));
    }

    @Test
    @DisplayName("시간선호도와 제안시간 개수를 설정할 경우 정해진 값과 개수를 순서대로 반환한다. ")
    void suggestTime_applySetting() {
        /**
         * given -> 19:00~21:00, 20:00~22:00, minuteUnit = TEN, duration = 30, sequencePriority = LATE, limit = 2
         * return -> 20:30~21:00, 20:20~20:50
         */
        //given
        Range range = Range.asCommonTime(FIXED_DATE, FIXED_DATE);
        RoughlyPlan roughlyPlan = new RoughlyPlan(range, 30, MinuteUnit.TEN);

        PlannerSetting plannerSetting = PlannerSetting.builder()
                .minuteUnit(MinuteUnit.TEN)
                .sequencePriority(SequencePriority.LATE)
                .limit(2)
                .build();

        List<RegularTimeBlock> block1 = RegularTimeBlockFixture.create(FIXED_DATE, "19:00~21:00");
        List<RegularTimeBlock> block2 = RegularTimeBlockFixture.create(FIXED_DATE, "20:00~22:00");

        WeeklyAvailablePlan weeklyPlan1 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block1);
        WeeklyAvailablePlan weeklyPlan2 = new WeeklyAvailablePlan((long)RandomUtil.getPositiveInt(),block2);
        List<TimeBlock> expected = TimeBlockFixture.createTimeBlocks(FIXED_DATE,
                "20:30~21:00",
                "20:20~20:50"
                );
        List<WeeklyAvailablePlan> weeklyPlans = List.of(weeklyPlan1, weeklyPlan2);
        //when
        List<TimeBlock> result = planner.suggestTime(roughlyPlan, weeklyPlans, null, plannerSetting);

        //then
        assertThat(result.size()).isEqualTo(expected.size());
        assertThat(result).containsSequence(expected.toArray(new TimeBlock[0]));
    }
    private static void todo() {
        assertThat(false).isTrue();
    }

}