package com.flab.quicktogether.timeplan.domain.value_type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.timeplan.domain.exception.NotNaturalTimeOrderException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 정기 시간블록으로 요일을 고려하여 항시 진행되는 타임블록을 나타냄
 * 타겟날짜를 지정할경우 해당하는 타임블록을 추출할 수 있음.
 *
 */
@Getter
@Embeddable
@EqualsAndHashCode
@ToString
public class RegularTimeBlock implements Comparable<RegularTimeBlock> {

    @NotNull
    @JsonProperty("day_of_week")
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @NotNull
    @JsonProperty("start_time")
    @Column(name = "start_time")
    private LocalTime startTime;

    @NotNull
    @JsonProperty("end_time")
    @Column(name = "end_time")
    private LocalTime endTime;


    protected RegularTimeBlock() {
    }

    private RegularTimeBlock(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        verifyNaturalTimeOrder(startTime, endTime);
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void verifyNaturalTimeOrder(LocalTime startTime, LocalTime endTime) {
        //endTime이 자정인경우 LocalTime이 가장 작은 값이므로 예외회피목적
        if (!(startTime.equals(LocalTime.MIN))
                && endTime.equals(LocalTime.MIN)) {
            return;
        }

        if (startTime.compareTo(endTime) >= 0) {
            throw new NotNaturalTimeOrderException();
        }
    }

    /**
     * 지역시간대로 받은 데이터를 기반으로 통일된 시간대로(UTC) 변환하여 객체를 생성하는 정적팩토리메서드
     * DB저장 및 연산을 위해서는 이 메서드로 작성된 RegularTimeBlock만 사용하여야 함.
     * 요일을 기준으로 하는 이산형 자료 특성에 따라, 하루를 걸쳐 넘어가는 시간대인 경우 시작요일과 종료요일이 달라질 수 있으므로 두개의 값이 반환될 여지가 있어, List로 반환함.
     *
     * @param localDayOfWeek 지역시간대 기준 요일
     * @param localStartTime 지역시간대 기준 시작시간
     * @param endTime 지역시간대 기준 종료시간
     * @param localTimeZone 지역시간대
     * @return 통일된 시간대로 변환된 RegularTimeBlock
     */
    public static List<RegularTimeBlock> asCommonTime(DayOfWeek localDayOfWeek, LocalTime localStartTime, LocalTime endTime, ZoneId localTimeZone) {
        ZoneId utc = ZoneId.of("UTC");
        LocalDate standardDate = getStandardDate(localDayOfWeek);

        ZonedDateTime startZonedDateTime = offset(standardDate,localStartTime,  localTimeZone, utc);
        ZonedDateTime endZonedDateTime = offset(standardDate, endTime, localTimeZone, utc);

        endZonedDateTime = checkMidnightEndTime(endTime, endZonedDateTime);

        return createRelativeTimeBlocks(startZonedDateTime, endZonedDateTime);
    }

    public List<RegularTimeBlock> toLocalTimeZone(ZoneId localTimeZone) {
        ZoneId utc = ZoneId.of("UTC");
        LocalDate standardDate = getStandardDate(dayOfWeek);

        ZonedDateTime startZonedDateTime = offset(standardDate, startTime, utc, localTimeZone);
        ZonedDateTime endZonedDateTime = offset(standardDate, endTime, utc, localTimeZone);

        endZonedDateTime = checkMidnightEndTime(endTime, endZonedDateTime);

        return createRelativeTimeBlocks(startZonedDateTime, endZonedDateTime);
    }

    private static ZonedDateTime offset( LocalDate standardDate, LocalTime time, ZoneId asIs, ZoneId toBe) {
        return ZonedDateTime
                .of(standardDate.atTime(time), asIs)
                .withZoneSameInstant(toBe);
    }

    private static ZonedDateTime checkMidnightEndTime(LocalTime endTime, ZonedDateTime endZonedDateTime) {
        if (endTime.equals(LocalTime.MIN)) {
            endZonedDateTime = endZonedDateTime.plusDays(1L);
        }
        return endZonedDateTime;
    }

    private static List<RegularTimeBlock> createRelativeTimeBlocks(ZonedDateTime startZonedDateTime, ZonedDateTime endZonedDateTime) {
        DayOfWeek startDayOfWeek = startZonedDateTime.getDayOfWeek();
        LocalTime startTime = startZonedDateTime.toLocalTime();

        DayOfWeek endDayOfWeek = endZonedDateTime.getDayOfWeek();
        LocalTime EndTime = endZonedDateTime.toLocalTime();

        return checkOverDate(startDayOfWeek, startTime, endDayOfWeek, EndTime);
    }

    // UTC변환시 하루를 걸치는 경우 각각의 날짜에 따로 저장해야함.
    private static List<RegularTimeBlock> checkOverDate(DayOfWeek startDayOfWeek, LocalTime startTime, DayOfWeek endDayOfWeek, LocalTime endTime) {
        List<RegularTimeBlock> utcRegularTimeBlocks = new ArrayList<>();

        if (startDayOfWeek.equals(endDayOfWeek)
                || endTime.equals(LocalTime.MIN)) {
            utcRegularTimeBlocks.add(new RegularTimeBlock(startDayOfWeek, startTime, endTime));
        } else {
            utcRegularTimeBlocks.add(new RegularTimeBlock(startDayOfWeek, startTime, LocalTime.MAX.plusNanos(1L)));
            utcRegularTimeBlocks.add(new RegularTimeBlock(endDayOfWeek, LocalTime.MIN, endTime));
        }
        return utcRegularTimeBlocks;
    }

    private static LocalDate getStandardDate(DayOfWeek dayOfWeek) {
        LocalDate standardDate = LocalDate.ofEpochDay(0);
        while (!standardDate.getDayOfWeek().equals(dayOfWeek)) {
            standardDate = standardDate.plusDays(1L);
        }
        return standardDate;
    }

    public static List<RegularTimeBlock> glue(List<RegularTimeBlock> regularTimeBlocks) {
        List<RegularTimeBlock> result = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            List<LocalTime> timeLine = new ArrayList<>();

            DayOfWeek dayOfWeek = DayOfWeek.of(i);
            List<RegularTimeBlock> equalDayOfWeekBlocks = regularTimeBlocks.stream()
                    .filter(block -> block.dayOfWeek == dayOfWeek)
                    .sorted()
                    .toList();

            if (equalDayOfWeekBlocks.isEmpty()) {
                continue;
            }

            Iterator<RegularTimeBlock> iterator = equalDayOfWeekBlocks.iterator();

            RegularTimeBlock cur = iterator.next();
            LocalTime curStartTime = cur.startTime;
            LocalTime curEndTime = cur.endTime;

            timeLine.add(curStartTime);
            timeLine.add(curEndTime);

            while(iterator.hasNext()){
                curEndTime = cur.endTime;

                RegularTimeBlock next = iterator.next();
                LocalTime nextStartDate = next.startTime;
                LocalTime nextEndDate = next.endTime;

                if (curEndTime.compareTo(nextStartDate) < 0) {
                    timeLine.add(nextStartDate);
                } else {
                    timeLine.remove(curEndTime);
                }
                timeLine.add(nextEndDate);

                cur = next;
            }

            Iterator<LocalTime> timeLineIterator = timeLine.iterator();
            LocalTime curTime = timeLineIterator.next();
            while (timeLineIterator.hasNext()) {
                LocalTime nextTime = timeLineIterator.next();
                RegularTimeBlock regularTimeBlock = RegularTimeBlock.of(dayOfWeek, curTime, nextTime);
                result.add(regularTimeBlock);

                if (timeLineIterator.hasNext()) {
                    curTime = timeLineIterator.next();
                }
            }
        }
        return result;

    }

    private static RegularTimeBlock of(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return new RegularTimeBlock(dayOfWeek, startTime, endTime);
    }

    private boolean canGlue(RegularTimeBlock regularTimeBlock) {
        return this.endTime.compareTo(regularTimeBlock.startTime) == 0;
    }

    public boolean isSeperatedFrom(RegularTimeBlock target) {
        return (this.startTime.compareTo(target.endTime) >= 0)
                || (this.endTime.compareTo(target.startTime) <= 0);
    }

    /**
     * 기간을 기준으로 날짜가 반영된 TimeBlock을 반환하는 메서드.
     * 스케줄링을 위해서는 날짜정보가 들어가야 실질적인 비교가 가능함.
     *
     * @param range 기간이 정해져야 날짜를 특정할 수 있음.
     * @return
     */
    public List<TimeBlock> extractTimeBlocks(Range range) {
        LocalDate startDate = range.getStartDateTime().toLocalDate();
        LocalDate endDate = range.getEndDateTime().toLocalDate();
        LocalDate endPoint = endDate.plusDays(1L);

        return startDate.datesUntil(endPoint)
                .filter(date -> date.getDayOfWeek().equals(dayOfWeek))
                .map(date -> getTimeBlock(date, range))
                .collect(Collectors.toList());
    }

    private TimeBlock getTimeBlock(LocalDate d, Range range) {
        LocalDateTime from = d.atTime(startTime);
        LocalDateTime to = getMidnightCheckedLocalDateTime(d);
        return TimeBlock.inRange(from, to, range);
    }

    //LocalDateTime은 24:00를 다음날 00:00로 넘기므로 자정인경우 다음날로 생성해주어야함.
    private LocalDateTime getMidnightCheckedLocalDateTime(LocalDate d) {
        if (endTime.equals(LocalTime.MIN)) {
            return d.plusDays(1L).atTime(endTime);
        }
        return d.atTime(endTime);
    }

    @Override
    public int compareTo(RegularTimeBlock o) {
        int compare = this.dayOfWeek.compareTo((o.dayOfWeek));
        if (compare == 0) {
            compare = this.startTime.compareTo(this.startTime);
            if (compare == 0) {
                compare = this.endTime.compareTo(o.endTime);
            }
        }
        return compare;
    }
}
