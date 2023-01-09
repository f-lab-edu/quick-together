package com.flab.quicktogether.timeplan.fixture;

import com.flab.quicktogether.timeplan.domain.AbleRoutine;
import com.flab.quicktogether.timeplan.domain.Event;
import com.flab.quicktogether.timeplan.domain.TimePlan;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import com.flab.quicktogether.member.domain.Member;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.flab.quicktogether.timeplan.fixture.TimeBlockFixture.*;

public class TimePlanFixture {

    public static final LocalDate FIXED_DATE = LocalDate.now().plusDays(1L);


    public static final Member TEST_MEMBER = new Member("test");

    public static final List<AbleRoutine> DAY_EQUAL_ROUTINES = getDayEqualAbleRoutine(
            "10:00~12:00",
                     "13:00~18:00",
                     "20:00~21:00");

    public static final List<Event> PLANNED_EVENTS = createPlannedEvents(
            "forwardOverlapEvent_09:00~10:01",
            "doubleOverlapEvent_11:40~13:30",
            "includeEvent_14:00~15:00",
            "coverEvent_19:00~23:00"
    );

    public static final List<TimeBlock> EXPECTED_ABLE_TIME_BLOCK = createAtbs(FIXED_DATE,
            "10:01~11:40",
                            "13:30~14:00",
                            "15:00~18:00");

    public static final List<AbleRoutine> DAY_OVERLAP_ROUTINES = getDayEqualAbleRoutine(
            "10:00~12:00",
            "11:59~13:00"
    );

    public static TimePlan TIME_PLAN = new TimePlan(TEST_MEMBER, DAY_EQUAL_ROUTINES, PLANNED_EVENTS);



    private static List<Event> createPlannedEvents(String... events) {
        return Arrays.stream(events).map(TimePlanFixture::createPlannedEvent).toList();
    }

    private static Event createPlannedEvent(String event) {
        String[] splitNameAndTime = event.split("_");
        String eventName = splitNameAndTime[0];
        String period = splitNameAndTime[1];

        return new Event(TIME_PLAN, event, createAtb(FIXED_DATE, period));
    }

    public static RegularTimeBlock createRegularTimeBlock(int dayOfWeekValue, String timeBlock) {
        DayOfWeek parsedDayOfWeek = DayOfWeek.of(dayOfWeekValue);

        String regex = "~";
        String[] split = timeBlock.split(regex);
        String startTimeString = split[0];
        String endTimeString = split[1];

        LocalTime parsedStartTime = LocalTime.parse(startTimeString);
        LocalTime parsedEndTime = LocalTime.parse(endTimeString);
        return new RegularTimeBlock(parsedDayOfWeek,parsedStartTime, parsedEndTime);
    }

    private static List<AbleRoutine> getDayEqualAbleRoutine(String...periods) {
        ArrayList<AbleRoutine> dayEqualAbleRoutine = new ArrayList<>();
        for (int i = 0; i < periods.length ; i++) {
            for (int j = 1; j <=7 ; j++) {
                AbleRoutine ableRoutine = new AbleRoutine(createRegularTimeBlock(j, periods[i]));
                dayEqualAbleRoutine.add(ableRoutine);
            }
        }
        return dayEqualAbleRoutine;
    }
    public static RegularTimeBlock createRegularTimeBlock(DayOfWeek dayOfWeek, String timeBlockString) {
        String regex = "~";
        String[] split = timeBlockString.split(regex);
        String startTimeString = split[0];
        String endTimeString = split[1];

        LocalTime parsedStartTime = LocalTime.parse(startTimeString);
        LocalTime parsedEndTime = LocalTime.parse(endTimeString);
        return new RegularTimeBlock(dayOfWeek,parsedStartTime, parsedEndTime);
    }

}
