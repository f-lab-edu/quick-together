package com.flab.quicktogether.timeplan.fixture;

import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.member.domain.Member;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.flab.quicktogether.timeplan.fixture.PlanFixture.randomName;
import static com.flab.quicktogether.timeplan.fixture.TimeBlockFixture.*;

public class AvailablePlanFixture {

    public static final ZoneId UTC = ZoneId.of("UTC");
    public static final LocalDate FIXED_DATE = LocalDate.now().plusDays(1L);
    public static final Range FIXED_DATE_BLOCK = Range.asCommonTime(FIXED_DATE, FIXED_DATE,UTC);


    public static final Member TEST_MEMBER = new Member("test");
    public static final Long TEST_MEMBER_ID = 1L;
    public static final List<RegularTimeBlock> DAY_EQUAL_AVAILABLE_PLAN = getEqualTimeWeeklyAvailablePlan(
            "10:00~12:00",
                     "13:00~18:00",
                     "20:00~21:00");

    public static final List<Plan> PLANS = createPlannedEvents(
            "forwardOverlapEvent_09:00~10:01",
            "doubleOverlapEvent_11:40~13:30",
            "includeEvent_14:00~15:00",
            "coverEvent_19:00~23:00"
    );

    public static final List<TimeBlock> EXPECTED_ABLE_TIME_BLOCK = createTimeBlocks(FIXED_DATE,
            "10:01~11:40",
                            "13:30~14:00",
                            "15:00~18:00");

    public static final List<RegularTimeBlock> DAY_OVERLAP_ROUTINES = getEqualTimeWeeklyAvailablePlan(
            "10:00~12:00",
            "11:59~13:00"
    );


    private static List<Plan> createPlannedEvents(String... events) {
        return Arrays.stream(events).map(AvailablePlanFixture::createPlannedEvent).toList();
    }

    private static Plan createPlannedEvent(String event) {
        String[] splitNameAndTime = event.split("_");
        String eventName = splitNameAndTime[0];
        String period = splitNameAndTime[1];

        return new Plan(1L, randomName(), newTimeBlockFixture(FIXED_DATE, period));
    }

    public static List<RegularTimeBlock> createRegularTimeBlock(int dayOfWeekValue, String timeBlock) {
        DayOfWeek parsedDayOfWeek = DayOfWeek.of(dayOfWeekValue);

        String regex = "~";
        String[] split = timeBlock.split(regex);
        String startTimeString = split[0];
        String endTimeString = split[1];

        LocalTime parsedStartTime = LocalTime.parse(startTimeString);
        LocalTime parsedEndTime = LocalTime.parse(endTimeString);
        return RegularTimeBlock.asCommonTime(parsedDayOfWeek,parsedStartTime, parsedEndTime, ZoneId.of("UTC"));
    }

    public static List<RegularTimeBlock> getEqualTimeWeeklyAvailablePlan(String...periods) {
        ArrayList<RegularTimeBlock> dayEqualAvailablePlan = new ArrayList<>();
        for (int i = 0; i < periods.length ; i++) {
            for (int j = 1; j <=7 ; j++) {
                dayEqualAvailablePlan.addAll(createRegularTimeBlock(j, periods[i]));
            }
        }
        return dayEqualAvailablePlan;
    }
    public static List<RegularTimeBlock> createRegularTimeBlock(DayOfWeek dayOfWeek, String timeBlockString) {
        String regex = "~";
        String[] split = timeBlockString.split(regex);
        String startTimeString = split[0];
        String endTimeString = split[1];

        LocalTime parsedStartTime = LocalTime.parse(startTimeString);
        LocalTime parsedEndTime = LocalTime.parse(endTimeString);
        return RegularTimeBlock.asCommonTime(dayOfWeek,parsedStartTime, parsedEndTime, ZoneId.of("UTC"));
    }

}
