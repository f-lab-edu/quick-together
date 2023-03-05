package com.flab.quicktogether.timeplan.fixture;

import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegularTimeBlockFixture {
    public static final ZoneId UTC = ZoneId.of("UTC");

    public static List<RegularTimeBlock> create(LocalDate date, String... fromTo) {
        return create(UTC, date, fromTo);
    }

    public static List<RegularTimeBlock> create(ZoneId localtimeZone, LocalDate localDate, String[] fromTo) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return Arrays.stream(fromTo)
                .flatMap(s -> convertTimeBlock(localtimeZone, dayOfWeek, s).stream())
                .collect(Collectors.toList());
    }

    private static List<RegularTimeBlock> convertTimeBlock(ZoneId timeZone, DayOfWeek dayOfWeek, String s) {
        String[] split = s.split("~");
        LocalTime startTime = LocalTime.parse(split[0]);
        LocalTime endTime = LocalTime.parse(split[1]);

        return RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, timeZone);
    }
}
