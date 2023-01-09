package com.flab.quicktogether.timeplan.domain;

import com.flab.quicktogether.timeplan.domain.exception.ExpiredTimeException;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.flab.quicktogether.timeplan.fixture.TimePlanFixture.TIME_PLAN;

public class EventTest {
    @Test
    @DisplayName("이벤트의 종료시간이 현재시간을 넘었으면, ExpiredTimeException을 발생한다.")
    public void newPlannedEvent_Expired() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.minusNanos(1L);

        LocalDateTime startTime = expiredTime.minusMinutes(10L);
        LocalDateTime expiredEndTime = expiredTime;

        TimeBlock atb = new TimeBlock(startTime, expiredEndTime);

        //when then
        Assertions.assertThrows(ExpiredTimeException.class, () -> new Event(TIME_PLAN,"meeting", atb));

    }
}
