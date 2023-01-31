package com.flab.quicktogether.timeplan.domain.plan;

import com.flab.quicktogether.timeplan.domain.exception.ExpiredTimeException;
import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.flab.quicktogether.timeplan.fixture.TimeBlockFixture.UTC;

public class PlanTest {
    @Test
    @DisplayName("이벤트의 종료시간이 현재시간을 넘었으면, ExpiredTimeException을 발생한다.")
    public void newPlannedEvent_Expired() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.minusNanos(1L);

        LocalDateTime startTime = expiredTime.minusMinutes(10L);
        LocalDateTime expiredEndTime = expiredTime;

        TimeBlock atb = TimeBlock.asCommonTime(startTime, expiredEndTime, UTC);

        //when then
        Assertions.assertThrows(ExpiredTimeException.class, () -> new Plan(1L, "meeting", atb));

    }


}
