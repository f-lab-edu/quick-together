package com.flab.quicktogether.timeplan.domain.weekly_available_plan;

import com.flab.quicktogether.timeplan.domain.weekly_available_plan.AvailablePlan;
import com.flab.quicktogether.timeplan.domain.exception.ExpiredTimeException;
import com.flab.quicktogether.timeplan.domain.value_type.Range;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;

class AvailablePlanTest {

    @Test
    @DisplayName("오늘과 같거나 이전날짜를 Range에 포함하는 경우 ExpiredTimeException을 던진다.")
    public void extractTimeBlocks_throwExpire() throws Exception{
        //given
        LocalTime ableStartTime = LocalTime.parse("18:00");
        LocalTime ableEndTime = LocalTime.parse("20:00");

        LocalDate expiredDate = LocalDate.now().minusDays(1L);

        LocalDate targetStartDate = LocalDate.now().minusDays(3L);
        LocalDate targetEndDate = expiredDate;
        Range targetDate = Range.asCommonTime(targetStartDate, targetEndDate, UTC);
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        DayOfWeek dayOfWeek = targetStartDate.getDayOfWeek();

        List<AvailablePlan> collect = RegularTimeBlock.asCommonTime(dayOfWeek, ableStartTime, ableEndTime, zoneId).stream()
                .map(AvailablePlan::new)
                .collect(Collectors.toList());

        //when&then
        Assertions.assertThrows(ExpiredTimeException.class,
                () -> collect.get(0).extractAvailableTime(targetDate));
    }
}