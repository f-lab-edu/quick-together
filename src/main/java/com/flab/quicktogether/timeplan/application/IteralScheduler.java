package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import com.flab.quicktogether.timeplan.domain.etc.MinuteUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class IteralScheduler implements Scheduler {

    @Override
    public List<TimeBlock> suggestEventTime(MinuteUnit minuteUnit, LocalDate roughStartDate, LocalDate roughEndDate, Integer EventPeriodByMinute,List<List<TimeBlock>> ableTimeblocks) {

        //1. 기간과 단위시간만큼의 후보시간 얻기
        List<TimeBlock> candidateTimes = getCandidateTimes(minuteUnit, roughStartDate, roughEndDate, EventPeriodByMinute);

        //2. 후보시간과 가능시간들을 모두 비교하여 시간제안
        return findTimeBlocksAllIncluded(candidateTimes, ableTimeblocks);
    }

    /**
    * CandidateTime은 각멤버들의 가능시간이 포함되는지 여부를 확인하기 위한 중간연산값
    * */
    private List<TimeBlock> getCandidateTimes(MinuteUnit minuteUnit, LocalDate targetStartDate, LocalDate targetEndDate, Integer durationMinutes) {

        int unitValue = minuteUnit.getUnitValue();

        LocalDateTime endTarget = LocalDateTime.of(targetEndDate.plusDays(1L),LocalTime.MIN)
                .minusHours(getHour(durationMinutes))
                .minusMinutes(getMinute(durationMinutes));

        List<TimeBlock> candidateTimes = new ArrayList<>();

        LocalDateTime target = LocalDateTime.of(targetStartDate, LocalTime.MIN);
        while (target.compareTo(endTarget) <= 0) {
            candidateTimes.add(new TimeBlock(target, target.plusMinutes(durationMinutes)));
            target = target.plusMinutes(unitValue); // TODO LocalDateTime이 불변객체라 Iterate시 GC부담이 계속될듯 해결필요
        }
        return candidateTimes;
    }

    private List<TimeBlock> findTimeBlocksAllIncluded(List<TimeBlock> candidateTimes, List<List<TimeBlock>> ableTimeBlocks) {
        return candidateTimes.stream()
                .filter(candidateTime -> ableTimeBlocks.stream()
                        .allMatch(candidateTime::isIncludeIn))
                .toList();

    }

    private int getHour(Integer minute) {
        return minute / 60;
    }

    private int getMinute(Integer minute) {
        return minute % 60;
    }
}
