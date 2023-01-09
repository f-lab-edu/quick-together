package com.flab.quicktogether.timeplan.domain.value_type;

import com.flab.quicktogether.timeplan.domain.exception.NotNaturalTimeOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class RegularTimeBlockTest {
    @Test
    @DisplayName("절대시간반환시 타겟시작점이 종료점보다 이후에 시작하면 IllegalRangeOrderException")
    public void generateAbsoluteTimeBlocks() throws Exception {

        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime startTime = LocalTime.of(16, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        LocalDate targetStartTime = LocalDate.now();
        LocalDate targetEndDate = targetStartTime.minusDays(1L);

        RegularTimeBlock sut = new RegularTimeBlock(dayOfWeek, startTime, endTime);

        //when & then
        Assertions.assertThrows(NotNaturalTimeOrderException.class, () -> sut.generateAbsoluteTimeBlocks(targetStartTime, targetEndDate));


    }

    @Test
    @DisplayName("범위가 같은 날짜를 가리키면 그 날짜의 가능시간을 반환한다.")
    public void createAbsoluteTime() throws Exception {

        //given
        LocalTime startTime = LocalTime.parse("18:00");
        LocalTime endTime = LocalTime.parse("20:00");

        LocalDate targetStartDate = LocalDate.now().plusDays(1L); // 금일로부터 기간이 지나면 절대시간을 generate할 수 없음.
        LocalDate targetEndDate = targetStartDate;
        DayOfWeek dayOfWeek = targetStartDate.getDayOfWeek();

        RegularTimeBlock regularTimeBlock = new RegularTimeBlock(dayOfWeek, startTime, endTime);

        //AbsoluteTimeBlock 생성
        LocalDateTime expectedStartDateTime = targetStartDate.atTime(startTime);
        LocalDateTime expectedEndDateTime = targetStartDate.atTime(endTime);
        TimeBlock expectedTimeBlock = new TimeBlock(expectedStartDateTime, expectedEndDateTime);

        //when
        List<TimeBlock> result = regularTimeBlock.generateAbsoluteTimeBlocks(targetStartDate, targetEndDate);
        TimeBlock resultTimeBlock = result.get(0);

        //then
        assertThat(resultTimeBlock).isEqualTo(expectedTimeBlock);
    }

    @Test
    @DisplayName("여러기간에 걸쳐 절대시간을 요청하면 해당기간의 모든 리스트를 반환한다.")
    public void createAbsoluteTime_multiple() throws Exception{
        //given
        LocalTime startTime = LocalTime.parse("18:00");
        LocalTime endTime = LocalTime.parse("20:00");

        LocalDate targetStartDate = LocalDate.now().plusDays(1L); // 금일로부터 기간이 지나면 절대시간을 generate할 수 없음.
        LocalDate targetEndDate = targetStartDate.plusWeeks(3L);
        int expectedCount = 4;

        DayOfWeek dayOfWeek = targetStartDate.getDayOfWeek();

        RegularTimeBlock regularTimeBlock = new RegularTimeBlock(dayOfWeek, startTime, endTime);

        LocalDateTime expectedStartDateTime = targetStartDate.atTime(startTime);
        LocalDateTime expectedEndDateTime = targetStartDate.atTime(endTime);
        TimeBlock expectedTimeBlock = new TimeBlock(expectedStartDateTime, expectedEndDateTime);

        List<TimeBlock> expectedTimeBlocks = Stream.iterate(0, i -> i + 1)
                .limit(expectedCount)
                .map(i -> createNextAbsoluteTimeBlockByWeeks(expectedTimeBlock, i))
                .toList();
        //when
        List<TimeBlock> result = regularTimeBlock.generateAbsoluteTimeBlocks(targetStartDate, targetEndDate);
        result.forEach(System.out::println);
        //then
        assertThat(result.size()).isEqualTo(expectedCount);
        assertThat(result).isEqualTo(expectedTimeBlocks);

    }

    private TimeBlock createNextAbsoluteTimeBlockByWeeks(TimeBlock expectedTimeBlock, int expectedCount) {
        LocalDateTime startDateTime = expectedTimeBlock.getStartDateTime();
        LocalDateTime endDateTime = expectedTimeBlock.getEndDateTime();
        return new TimeBlock(startDateTime.plusWeeks((long) expectedCount), endDateTime.plusWeeks((long) expectedCount));
    }



}