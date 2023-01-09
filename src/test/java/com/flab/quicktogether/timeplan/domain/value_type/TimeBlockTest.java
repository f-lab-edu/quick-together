package com.flab.quicktogether.timeplan.domain.value_type;

import com.flab.quicktogether.timeplan.domain.exception.NotNaturalTimeOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.flab.quicktogether.timeplan.fixture.TimeBlockFixture.*;
import static org.assertj.core.api.Assertions.*;

class TimeBlockTest {

    @Test
    @DisplayName("종료시간이 시작시간을 앞서거나 같은 경우 생성시 IllegalOrderTimeBlockException을 반환한다.")
    void newTimeBlock_ThrowIllegalOrder() throws Exception{
        //given
        LocalDateTime now = NOW_DATE_TIME;
        LocalDateTime beforeDateTime = BEFORE_DATE_TIME;

        //when & then
        Assertions.assertThrows(NotNaturalTimeOrderException.class, () -> new TimeBlock(now, beforeDateTime));
        Assertions.assertThrows(NotNaturalTimeOrderException.class, () -> new TimeBlock(now, now));
    }

    @Test
    @DisplayName("같은 AbsoluteTimeBlock을 trim하면 빈 배열을 반환한다.")
    void trim_includeEqual() throws Exception {

        //given
        TimeBlock normal = NORMAL;

        TimeBlock equal = EQUAL;

        //when
        List<TimeBlock> result = normal.trim(equal);

        //then
        assertThat(result.size()).isEqualTo(0);
        assertThat(result).isEmpty();

    }


    @Test
    @DisplayName("시작점이 같고 끝점이 작은 값을 trim할 경우 앞부분이 잘린 TimeBlock이 1개 생성된다.")
    void trim_startSameOverlap() throws Exception{
        //given
        TimeBlock normal = NORMAL;
        TimeBlock startSideSameOverlap = START_SIDE_SAME_OVERLAP;

        TimeBlock expected = START_SIDE_SAME_OVERLAP_EXPECT;
        //when
        List<TimeBlock> result = normal.trim(startSideSameOverlap);

        //then
        assertThat(result).containsExactly(expected);
    }

    @Test
    @DisplayName("끝점이 같고 시작점이 나중인 값을 trim할 경우 뒷부분이 잘린 TimeBlock이 1개 생성된다.")
    void trim_endSameOverlap() throws Exception{
        //given
        TimeBlock normal = NORMAL;
        TimeBlock endSideSameOverlap = END_SIDE_SAME_OVERLAP;

        TimeBlock expected = END_SIDE_SAME_OVERLAP_EXPECT;

        //when
        List<TimeBlock> result = normal.trim(endSideSameOverlap);

        //then
        assertThat(result).containsExactly(expected);
    }


    @Test
    @DisplayName("값에 포함되는 값을 trim할 경우 각 시작점을 구간으로한 TimeBlock과 끝점을 구간으로한 Timeblock이 각각 하나씩 생성된다.")
    void trim_include() throws Exception{
        //given
        TimeBlock normal = NORMAL;
        TimeBlock include = INCLUDE;

        TimeBlock expected1 = INCLUDE_EXPECT1;
        TimeBlock expected2 = INCLUDE_EXPECT2;

        //when
        List<TimeBlock> result = normal.trim(include);

        //then
        assertThat(result).containsExactly(expected1,expected2);
    }


    @Test
    @DisplayName("시작점이 앞에있지만 끝점이 사이에있는 값을 trim할 경우 앞부분이 잘린 TimeBlock이 1개 생성된다.")
    void trim_overlapForward() throws Exception{
        //given
        TimeBlock normal = NORMAL;
        TimeBlock overlapForward = OVERLAP_FORWARD;

        TimeBlock expected = OVERLAP_FORWARD_EXPECT;

        //when
        List<TimeBlock> result = normal.trim(overlapForward);

        //then
        assertThat(result).containsExactly(expected);
    }


    @Test
    @DisplayName("끝점이 뒤에있지만 시작점이 사이에 있는 값을 trim할 경우 뒷부분이 잘린 TimeBlock이 1개 생성된다.")
    void trim_overlapBackward() throws Exception{
        //given
        TimeBlock normal = NORMAL;
        TimeBlock overlapBackward = OVERLAP_BACKWARD;

        TimeBlock expected = OVERLAP_BACKWARD_EXPECT;

        //when
        List<TimeBlock> result = normal.trim(overlapBackward);

        //then
        assertThat(result).containsExactly(expected);
    }

    @Test
    @DisplayName("서로 만나지 않는 값을 trim할 경우 기존 값을 그대로 반환한다.")
    void trim_apart() throws Exception{
        //given
        TimeBlock normal = NORMAL;
        TimeBlock apart = APART;

        TimeBlock expected = APART_EXPECT;

        //when
        List<TimeBlock> result = normal.trim(apart);

        //then
        assertThat(result).containsExactly(expected);
    }


    @Test
    @DisplayName("서로 붙어있는 값을 trim할 경우 기존값을 그대로 반환한다. ")
    void trim_continue() throws Exception {
        //given
        TimeBlock normal = NORMAL;
        TimeBlock aContinue = CONTINUE;

        TimeBlock expected = CONTINUE_EXPECT;

        //when
        List<TimeBlock> result = normal.trim(aContinue);

        //then
        assertThat(result).containsExactly(expected);
    }

    @Test
    @DisplayName("타임블록리스트가 포함되는지 확인하면 포함되는 블록이 하나라도 존재하는 경우 참을 반환한다.")
    void isIncludeIn_listCompare() {
        LocalDate target = LocalDate.now().plusDays(1L);

        LocalDateTime startTime1 = LocalDateTime.of(target, LocalTime.parse("19:00"));
        LocalDateTime endTime1 = LocalDateTime.of(target, LocalTime.parse("21:00"));

        LocalDateTime startTime2 = LocalDateTime.of(target, LocalTime.parse("20:00"));
        LocalDateTime endTime2 = LocalDateTime.of(target, LocalTime.parse("22:00"));

        TimeBlock block1 = new TimeBlock(startTime1, endTime1);
        TimeBlock block2 = new TimeBlock(startTime2, endTime2);


        LocalDateTime includedStartTime = LocalDateTime.of(target, LocalTime.parse("20:00"));
        LocalDateTime includedEndTime = LocalDateTime.of(target, LocalTime.parse("21:00"));

        TimeBlock includedTimeBlock = new TimeBlock(includedStartTime, includedEndTime);

        List<TimeBlock> blocks = List.of(block1, block2);

        boolean result = includedTimeBlock.isIncludeIn(blocks);

        assertThat(result).isTrue();
    }

}