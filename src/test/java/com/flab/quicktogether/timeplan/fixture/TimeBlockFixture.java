package com.flab.quicktogether.timeplan.fixture;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class TimeBlockFixture {

    public static final LocalDate NOW_DATE = LocalDate.now();

    public static final LocalDateTime NOW_DATE_TIME = LocalDateTime.now();
    public static final LocalDateTime BEFORE_DATE_TIME = LocalDateTime.now().minusMinutes(1L);

    public static final TimeBlock NORMAL = createAtb(NOW_DATE, "10:00~13:00");

    public static final TimeBlock EQUAL = createAtb(NOW_DATE, "10:00~13:00");
    public static final TimeBlock EQUAL_EXPECT = null;
    
    public static final TimeBlock START_SIDE_SAME_OVERLAP = createAtb(NOW_DATE, "10:00~10:20");
    public static final TimeBlock START_SIDE_SAME_OVERLAP_EXPECT = createAtb(NOW_DATE, "10:20~13:00");
    
    public static final TimeBlock END_SIDE_SAME_OVERLAP = createAtb(NOW_DATE, "12:50~13:00");
    public static final TimeBlock END_SIDE_SAME_OVERLAP_EXPECT = createAtb(NOW_DATE, "10:00~12:50");
    
    public static final TimeBlock INCLUDE = createAtb(NOW_DATE, "10:01~12:59");
    public static final TimeBlock INCLUDE_EXPECT1 = createAtb(NOW_DATE, "10:00~10:01");
    public static final TimeBlock INCLUDE_EXPECT2 = createAtb(NOW_DATE, "12:59~13:00");
    
    public static final TimeBlock OVERLAP_FORWARD = createAtb(NOW_DATE, "09:00~11:00");
    public static final TimeBlock OVERLAP_FORWARD_EXPECT = createAtb(NOW_DATE, "11:00~13:00");
    
    public static final TimeBlock OVERLAP_BACKWARD = createAtb(NOW_DATE, "12:59~14:00");
    public static final TimeBlock OVERLAP_BACKWARD_EXPECT = createAtb(NOW_DATE, "10:00~12:59");
    
    public static final TimeBlock CONTINUE = createAtb(NOW_DATE, "13:00~14:00");
    public static final TimeBlock CONTINUE_EXPECT = createAtb(NOW_DATE, "10:00~13:00");

    public static final TimeBlock APART = createAtb(NOW_DATE, "15:00~16:00");
    public static final TimeBlock APART_EXPECT = createAtb(NOW_DATE, "10:00~13:00");

    public static TimeBlock createAtb(LocalDate target, String timeBlockString) {
        String[] split = timeBlockString.split("~");
        String startTimeString = split[0];
        String endTimeString = split[1];

        return new TimeBlock(
                LocalDateTime.of(target, LocalTime.parse(startTimeString))
                , LocalDateTime.of(target, LocalTime.parse(endTimeString)));
    }

    public static List<TimeBlock> createAtbs(LocalDate target, String... timeBlockStrings) {
        return Arrays.stream(timeBlockStrings).map(s -> createAtb(target, s)).toList();
    }
}
