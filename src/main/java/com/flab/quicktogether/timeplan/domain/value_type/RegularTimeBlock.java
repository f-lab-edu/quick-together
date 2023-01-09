package com.flab.quicktogether.timeplan.domain.value_type;

import com.flab.quicktogether.timeplan.domain.exception.NotNaturalTimeOrderException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 정기 시간블록으로 요일을 고려하여 항시 진행되는 타임블록을 나타냄
 * 타겟날짜를 지정할경우 해당하는 타임블록을 추출할 수 있음.
 *
 * TODO
 * 요일단위로만 지정할수있으나, 하루를 걸쳐있는 정기시간도 존재할 수 있으므로 추후 개선이 필요함.
 */
@Getter
@Embeddable
@EqualsAndHashCode
@ToString
public class RegularTimeBlock implements Comparable<RegularTimeBlock> {

    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    protected RegularTimeBlock() {
    }

    public RegularTimeBlock(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isSeperatedFrom(RegularTimeBlock target) {
        return (this.startTime.compareTo(target.endTime) >= 0)
                || (this.endTime.compareTo(target.startTime) <= 0);
    }

    /**
     * 상대시간블록은 특정날짜와 겹치는 시간을 비교하기 위해서 절대시간으로 변경이 필요함.
     * @param targetStartDate 절대시간을 만들 대상의 시작일
     * @param targetEndDate 절대시간 만들 대상의 종료일
     * @return 절대시간 리스트
     */
    public List<TimeBlock> generateAbsoluteTimeBlocks(LocalDate targetStartDate, LocalDate targetEndDate) {
        validOrder(targetStartDate, targetEndDate);

        List<LocalDate> localDates = generateLocalDates(targetStartDate,targetEndDate);

        return localDates.stream()
                .map(this::convertAbsoluteTimeBlock)
                .collect(Collectors.toList());
    }

    private void validOrder(LocalDate targetStartDate, LocalDate targetEndDate) {
        if (targetStartDate.compareTo(targetEndDate) > 0) {
            throw new NotNaturalTimeOrderException();
        }
    }

    private TimeBlock convertAbsoluteTimeBlock(LocalDate date) {
        return new TimeBlock(date.atTime(startTime), date.atTime(endTime));
    }

    private List<LocalDate> generateLocalDates(LocalDate targetStartDate, LocalDate targetEndDate) {
        int thisDayOfWeekValue = this.dayOfWeek.getValue();

        return targetStartDate.datesUntil(targetEndDate.plusDays(1L))
                .filter(d -> d.getDayOfWeek().getValue() == thisDayOfWeekValue)
                .toList();
    }

    @Override
    public int compareTo(RegularTimeBlock o) {
        int compare = this.dayOfWeek.compareTo(o.dayOfWeek);
        if (compare == 0) {
            compare = this.startTime.compareTo(o.startTime);
            if (compare == 0) {
                compare = this.endTime.compareTo(o.endTime);
            }
        }
        return compare;
    }
}
