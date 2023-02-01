package com.flab.quicktogether.timeplan.domain;

import com.flab.quicktogether.timeplan.domain.exception.ExpiredTimeException;
import com.flab.quicktogether.timeplan.domain.value_type.RegularTimeBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

class AbleRoutineTest {

    @Test
    @DisplayName("오늘보다 이전종료일자를 기준으로 절대값을 반환하면 ExpiredTimeException을 던진다.")
    public void generateAblsoluteRoutine_throwExpire() throws Exception{
        //given
        LocalTime ableStartTime = LocalTime.parse("18:00");
        LocalTime ableEndTime = LocalTime.parse("20:00");

        LocalDate expiredDate = LocalDate.now().minusDays(1L);

        LocalDate targetStartDate = LocalDate.now().minusDays(3L);
        LocalDate targetEndDate = expiredDate;

        DayOfWeek dayOfWeek = targetStartDate.getDayOfWeek();

        AbleRoutine ableRoutine = new AbleRoutine(new RegularTimeBlock(dayOfWeek, ableStartTime, ableEndTime));

        //when&then
        Assertions.assertThrows(ExpiredTimeException.class,
                () -> ableRoutine.generateAbleTimeBlock(targetStartDate, targetEndDate));
    }
}