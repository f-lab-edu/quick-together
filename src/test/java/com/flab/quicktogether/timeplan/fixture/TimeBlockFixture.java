package com.flab.quicktogether.timeplan.fixture;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class TimeBlockFixture {
    public static final ZoneId UTC = ZoneId.of("UTC");
    public static final LocalDate NOW_DATE = LocalDate.now();
    public static final LocalDate LEFT_DATE = LocalDate.now().minusDays(1L);
    public static final LocalDateTime NOW_DATE_TIME = LocalDateTime.now();
    public static final LocalDateTime BEFORE_DATE_TIME = LocalDateTime.now().minusMinutes(1L);


    public static final TimeBlock NORMAL = newTimeBlockFixture(NOW_DATE, "10:00~13:00");

    public static final TimeBlock EQUAL = newTimeBlockFixture(NOW_DATE, "10:00~13:00");
    public static final TimeBlock EQUAL_EXPECT = null;
    
    public static final TimeBlock START_SIDE_SAME_OVERLAP = newTimeBlockFixture(NOW_DATE, "10:00~10:20");
    public static final TimeBlock START_SIDE_SAME_OVERLAP_EXPECT = newTimeBlockFixture(NOW_DATE, "10:20~13:00");
    
    public static final TimeBlock END_SIDE_SAME_OVERLAP = newTimeBlockFixture(NOW_DATE, "12:50~13:00");
    public static final TimeBlock END_SIDE_SAME_OVERLAP_EXPECT = newTimeBlockFixture(NOW_DATE, "10:00~12:50");
    
    public static final TimeBlock INCLUDE = newTimeBlockFixture(NOW_DATE, "10:01~12:59");
    public static final TimeBlock INCLUDE_EXPECT1 = newTimeBlockFixture(NOW_DATE, "10:00~10:01");
    public static final TimeBlock INCLUDE_EXPECT2 = newTimeBlockFixture(NOW_DATE, "12:59~13:00");
    
    public static final TimeBlock OVERLAP_FORWARD = newTimeBlockFixture(NOW_DATE, "09:00~11:00");
    public static final TimeBlock OVERLAP_FORWARD_EXPECT = newTimeBlockFixture(NOW_DATE, "11:00~13:00");
    
    public static final TimeBlock OVERLAP_BACKWARD = newTimeBlockFixture(NOW_DATE, "12:59~14:00");
    public static final TimeBlock OVERLAP_BACKWARD_EXPECT = newTimeBlockFixture(NOW_DATE, "10:00~12:59");
    
    public static final TimeBlock CONTINUE = newTimeBlockFixture(NOW_DATE, "13:00~14:00");
    public static final TimeBlock CONTINUE_EXPECT = newTimeBlockFixture(NOW_DATE, "10:00~13:00");

    public static final TimeBlock APART = newTimeBlockFixture(NOW_DATE, "15:00~16:00");
    public static final TimeBlock APART_EXPECT = newTimeBlockFixture(NOW_DATE, "10:00~13:00");

    public static TimeBlock newTimeBlockFixture(LocalDate target, String timeBlockString) {
        String[] split = timeBlockString.split("~");
        String startTimeString = split[0];
        String endTimeString = split[1];

        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalTime endTime = LocalTime.parse(endTimeString);

        LocalDateTime startDateTime = target.atTime(startTime);
        LocalDateTime endDateTime = target.atTime(endTime);

        if (endTime.equals(LocalTime.MIN)) {
            endDateTime = endDateTime.plusDays(1L);
        }

        return TimeBlock.asCommonTime(startDateTime, endDateTime, UTC);
    }

    public static List<TimeBlock> createTimeBlocks(LocalDate target, String... timeBlockStrings) {
        return Arrays.stream(timeBlockStrings).map(s -> newTimeBlockFixture(target, s)).toList();
    }
}
