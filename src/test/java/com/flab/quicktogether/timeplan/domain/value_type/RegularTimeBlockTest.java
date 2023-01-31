package com.flab.quicktogether.timeplan.domain.value_type;

import com.flab.quicktogether.timeplan.domain.exception.NotNaturalTimeOrderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class RegularTimeBlockTest {
    public static ZoneId UTC = ZoneId.of("UTC");
    public static ZoneId KST = ZoneId.of("Asia/Seoul");
    @Test
    @DisplayName("startTime이 endTime보다 이후인 RelativeTimeBlock을 생성할시 NotNaturalTimeOrderException을 반환한다.")
    void asCommonTime_startTimeIsAfterEndTime_throwNotNaturalTimeOrderException() throws Exception{
        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime startTime = LocalTime.parse("11:00");
        LocalTime endTime = LocalTime.parse("10:00");

        //when
        Exception result = catchException(() -> RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, UTC));

        //then
        assertThat(result).isInstanceOf(NotNaturalTimeOrderException.class);

    }


    @Test
    @DisplayName("endTime이 00:00시인 경우 예외를 반환치 않고 정상적으로 객체를 생성한다.")
    void asCommonTime_endTimeIsMidNight_NotThrownException() throws Exception{
        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime startTime = LocalTime.parse("11:00");
        LocalTime endTime = LocalTime.parse("00:00");

        //when
        RegularTimeBlock result = RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, UTC).get(0);

        //then
        assertThat(result.getDayOfWeek()).isEqualTo(dayOfWeek);
        assertThat(result.getStartTime()).isEqualTo(startTime);
        assertThat(result.getEndTime()).isEqualTo(endTime);

    }


    @Test
    @DisplayName("UTC변환 이후로 startTime과 endTime의 요일이 다른경우 각각 해당요일의 startTime에서 자정0시, 0시부터 endTime까지 두개의 블록을 생성한다.")
    void asCommonTime_utcConvertResultIsOverDayOfWeek_create2Blocks() throws Exception{
        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime startTime = LocalTime.parse("06:00");
        LocalTime endTime = LocalTime.parse("11:00");

        RegularTimeBlock expected1 = RegularTimeBlock.asCommonTime(dayOfWeek.minus(1L), LocalTime.parse("21:00"), LocalTime.parse("00:00"), UTC).get(0);
        RegularTimeBlock expected2 = RegularTimeBlock.asCommonTime(dayOfWeek, LocalTime.parse("00:00"), LocalTime.parse("02:00"), UTC).get(0);

        //when
        List<RegularTimeBlock> result = RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, KST);
        RegularTimeBlock result1 = result.get(0);
        RegularTimeBlock result2 = result.get(1);

        //then
        assertThat(result1).isEqualTo(expected1);
        assertThat(result2).isEqualTo(expected2);

    }

    @Test
    @DisplayName("날짜가 하루차이 나지만 각각 자정을 겸하고 있는 경우 TimeBlock추출시 겹쳐진부분을 하나로 묶어서 반환한다. ")
    void asCommonTime_mergePoint() throws Exception{
        //given
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        LocalTime startTime = LocalTime.parse("21:00");
        LocalTime endTime = LocalTime.parse("00:00");

        DayOfWeek expectedDayOfWeek = dayOfWeek;
        LocalTime expectedStartTime = LocalTime.parse("12:00");
        LocalTime expectedEndTime = LocalTime.parse("15:00");

        //when
        RegularTimeBlock result = RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, KST).get(0);

        //then
        assertThat(result.getDayOfWeek()).isEqualTo(expectedDayOfWeek);
        assertThat(result.getStartTime()).isEqualTo(expectedStartTime);
        assertThat(result.getEndTime()).isEqualTo(expectedEndTime);

    }


    @Test
    @DisplayName("범위가 같은 날짜를 가리키면 그 날짜의 가능시간을 반환한다.")
    void extractTimeBlocks_singleDate() throws Exception {
        //given
        LocalTime startTime = LocalTime.parse("18:00");
        LocalTime endTime = LocalTime.parse("20:00");

        LocalDate targetStartDate = LocalDate.now().plusDays(1L);
        LocalDate targetEndDate = targetStartDate;

        Range targetRange = Range.asCommonTime(targetStartDate, targetEndDate, UTC);

        DayOfWeek dayOfWeek = targetStartDate.getDayOfWeek();

        List<RegularTimeBlock> regularTimeBlocks = RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, UTC);

        //TimeBlock 생성
        LocalDateTime expectedStartDateTime = targetStartDate.atTime(startTime);
        LocalDateTime expectedEndDateTime = targetStartDate.atTime(endTime);
        TimeBlock expectedTimeBlock = TimeBlock.asCommonTime(expectedStartDateTime, expectedEndDateTime,UTC);

        //when
        List<TimeBlock> result = regularTimeBlocks.get(0).extractTimeBlocks(targetRange);
        TimeBlock resultTimeBlock = result.get(0);

        //then
        assertThat(resultTimeBlock).isEqualTo(expectedTimeBlock);

    }

    @Test
    @DisplayName("endTime이 정각인 블록을 추출할경우 endTime은 정각이 반영된 하루 이후의 가능시간을 반환한다.")
    void extractTimeBlocks_singleDateAndMidNightEndTime() throws Exception {
        //given
        LocalTime startTime = LocalTime.parse("18:00");
        LocalTime endTime = LocalTime.parse("00:00");

        LocalDate targetStartDate = LocalDate.now().plusDays(1L);
        LocalDate targetEndDate = targetStartDate;

        Range targetRange = Range.asCommonTime(targetStartDate, targetEndDate, UTC);

        DayOfWeek dayOfWeek = targetStartDate.getDayOfWeek();

        List<RegularTimeBlock> regularTimeBlocks = RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, UTC);

        //TimeBlock 생성
        LocalDateTime expectedStartDateTime = targetStartDate.atTime(startTime);
        LocalDateTime expectedEndDateTime = targetStartDate.plusDays(1L).atTime(endTime);
        TimeBlock expectedTimeBlock = TimeBlock.asCommonTime(expectedStartDateTime, expectedEndDateTime,UTC);

        //when
        List<TimeBlock> result = regularTimeBlocks.get(0).extractTimeBlocks(targetRange);
        TimeBlock resultTimeBlock = result.get(0);

        //then
        assertThat(resultTimeBlock).isEqualTo(expectedTimeBlock);

    }


    @Test
    @DisplayName("3주기간에 걸쳐 절대시간을 요청하면 해당기간의 모든 리스트를 반환한다.")
    void extractTimeBlock_multiple() throws Exception{
        //given
        LocalTime startTime = LocalTime.parse("18:00");
        LocalTime endTime = LocalTime.parse("20:00");

        LocalDate targetStartDate = LocalDate.now().plusDays(1L); // 금일로부터 기간이 지나면 절대시간을 generate할 수 없음.
        LocalDate targetEndDate = targetStartDate.plusWeeks(3L);
        Range targetRange = Range.asCommonTime(targetStartDate, targetEndDate, UTC);
        int expectedCount = 4; // 날짜가 같은날부터 시작하므로 블럭당 기본 1개가 존재하고 한주가 지날때마다 1씩 추가됨. 총 3주기간이므로 4개가 존재해야함.

        DayOfWeek dayOfWeek = targetStartDate.getDayOfWeek();

        List<RegularTimeBlock> regularTimeBlocks = RegularTimeBlock.asCommonTime(dayOfWeek, startTime, endTime, UTC);

        LocalDateTime expectedStartDateTime = targetStartDate.atTime(startTime);
        LocalDateTime expectedEndDateTime = targetStartDate.atTime(endTime);
        TimeBlock expectedTimeBlock = TimeBlock.asCommonTime(expectedStartDateTime, expectedEndDateTime,UTC);

        List<TimeBlock> expectedTimeBlocks = Stream.iterate(0, i -> i + 1)
                .limit(expectedCount)
                .map(i -> createNextAbsoluteTimeBlockByWeeks(expectedTimeBlock, i))
                .toList();
        //when
        List<TimeBlock> result = regularTimeBlocks.get(0).extractTimeBlocks(targetRange);
        //then
        assertThat(result.size()).isEqualTo(expectedCount);
        assertThat(result).isEqualTo(expectedTimeBlocks);

    }


    private TimeBlock createNextAbsoluteTimeBlockByWeeks(TimeBlock expectedTimeBlock, int expectedCount) {
        LocalDateTime startDateTime = expectedTimeBlock.getStartDateTime();
        LocalDateTime endDateTime = expectedTimeBlock.getEndDateTime();
        return TimeBlock.of(startDateTime.plusWeeks((long) expectedCount), endDateTime.plusWeeks((long) expectedCount));

    }


}