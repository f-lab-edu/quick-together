package com.flab.quicktogether.timeplan.domain.value_type;

import com.flab.quicktogether.timeplan.domain.exception.NotNaturalTimeOrderException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 범위를
 */
@Getter
@EqualsAndHashCode
@ToString
public final class Range {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    private Range(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static Range asCommonTime(LocalDate startDate, LocalDate endDate, ZoneId zoneId) {
        ZoneId utc = ZoneId.of("UTC");
        verifyNaturalDateOrder(startDate, endDate);

        LocalDateTime from = startDate
                .atStartOfDay(zoneId)
                .withZoneSameInstant(utc)
                .toLocalDateTime();

        LocalDateTime to = endDate
                .atStartOfDay(zoneId)
                .plusDays(1L)
                .withZoneSameInstant(utc)
                .toLocalDateTime();

        return new Range(from, to);
    }

    public static Range asCommonTime(LocalDate startDate, LocalDate endDate) {
        ZoneId utc = ZoneId.of("UTC");
        return asCommonTime(startDate, endDate, utc);
    }

    public static Range of(TimeBlock suggestionTime) {
        LocalDateTime from = suggestionTime.getStartDateTime();
        LocalDateTime to = suggestionTime.getEndDateTime();
        return new Range(from, to);
    }

    private static void verifyNaturalDateOrder(LocalDate startDate, LocalDate endDate) {
        if (startDate.compareTo(endDate) > 0) {
            throw new NotNaturalTimeOrderException();
        }
    }

}
