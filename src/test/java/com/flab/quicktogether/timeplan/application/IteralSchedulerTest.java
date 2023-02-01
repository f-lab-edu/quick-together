package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.etc.MinuteUnit;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IteralSchedulerTest {
    private final Scheduler scheduler = new IteralScheduler();

    @Test
    @DisplayName("두 타임블록을 두고 시간을 제안하면 모두 공통적으로 가능한 시간에 배정된다.")
    void suggestTime() {
        LocalDate target = LocalDate.now().plusDays(1L);
        // 19:00~ 21:00
        LocalDateTime startTime1 = LocalDateTime.of(target, LocalTime.parse("19:00"));
        LocalDateTime endTime1 = LocalDateTime.of(target, LocalTime.parse("21:00"));

        LocalDateTime startTime2 = LocalDateTime.of(target, LocalTime.parse("20:00"));
        LocalDateTime endTime2 = LocalDateTime.of(target, LocalTime.parse("22:00"));


        TimeBlock block1 = new TimeBlock(startTime1, endTime1);
        TimeBlock block2 = new TimeBlock(startTime2, endTime2);
        List<TimeBlock> blocks1 = List.of(block1);
        List<TimeBlock> blocks2 = List.of(block2);

        List<List<TimeBlock>> blocks = List.of(blocks1, blocks2);
        Integer eventPeriodByMinute = 60;

        //when
        List<TimeBlock> result = scheduler.suggestEventTime(MinuteUnit.TEN, target, target, eventPeriodByMinute, blocks);

        LocalDateTime expectedStartTime = LocalDateTime.of(target, LocalTime.parse("20:00"));
        LocalDateTime expectedEndTime = LocalDateTime.of(target, LocalTime.parse("21:00"));
        TimeBlock expectedTimeBlock = new TimeBlock(expectedStartTime, expectedEndTime);

        assertThat(result).containsExactly(expectedTimeBlock);
    }
}