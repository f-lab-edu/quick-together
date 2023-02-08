package com.flab.quicktogether.timeplan.domain.value_type;

import com.flab.quicktogether.timeplan.domain.exception.NotNaturalTimeOrderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.*;

class RangeTest {

    @Test
    @DisplayName("Range생성시 타겟시작점이 종료점보다 이후에 시작하면 NotNaturalTimeOrderException을 던진다.")
    void of_startDateIsAfterEndDate_throwNotNaturalTimeOrderException() throws Exception {

        //given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1L);
        ZoneId zoneId = ZoneId.of("UTC");

        //when
        Exception result = catchException(() -> Range.asCommonTime(startDate, endDate, zoneId));

        //then
        assertThat(result).isInstanceOf(NotNaturalTimeOrderException.class);
    }

    @Test
    @DisplayName("UTC로 종료시간이 자정인 경우 endDateTime의 날짜는 하루 증가되어 생성된다.")
    void of_utc_endDatePlusDay() throws Exception{
        //given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate;
        ZoneId zoneId = ZoneId.of("UTC");

        LocalDateTime expectedFrom = startDate.atStartOfDay();
        LocalDateTime expectedTo = endDate.atStartOfDay().plusDays(1L);
        //when
        Range result = Range.asCommonTime(startDate, endDate, zoneId);
        System.out.println("result = " + result);
        //then
        assertThat(result.getStartDateTime()).isEqualTo(expectedFrom);
        assertThat(result.getEndDateTime()).isEqualTo(expectedTo);
    }

    @Test
    @DisplayName("UTC변환으로 날짜가 바뀌는 경우 해당날짜를 반환한다.")
    void getMethod_WithZoneId() throws Exception{
        //given
        LocalDate targetStartDate = LocalDate.now();
        LocalDate targetEndDate = targetStartDate.plusDays(1L);
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDate expectedStartTime = targetStartDate.minusDays(1L);
        LocalDate expectedEndTime = targetEndDate
                .plusDays(1L) // UTC기준 LocalDateTime 최 끝은 하루증가되어 생성되어야함.
                .minusDays(1L); // UTC변환으로 인한 시간 조절

        //when
        Range result = Range.asCommonTime(targetStartDate, targetEndDate, zoneId);
        LocalDate resultStartDate = result.getStartDateTime().toLocalDate();
        LocalDate resultEndDate = result.getEndDateTime().toLocalDate();

        //then
        assertThat(resultStartDate).isEqualTo(expectedStartTime);
        assertThat(resultEndDate).isEqualTo(expectedEndTime);
    }

}