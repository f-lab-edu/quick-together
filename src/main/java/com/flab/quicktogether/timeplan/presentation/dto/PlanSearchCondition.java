package com.flab.quicktogether.timeplan.presentation.dto;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PlanSearchCondition {


    private final String from;

    private final String to;

    private final String timeZone;

    public LocalDateTime getFrom(){
        ZoneId zoneId = ZoneId.of(timeZone);
        LocalDateTime from = LocalDateTime.parse(this.from.replace("Z",""));
        return offsetUtc(from, zoneId);
    }

    public LocalDateTime getTo() {
        ZoneId zoneId = ZoneId.of(timeZone);
        LocalDateTime to = LocalDateTime.parse(this.to.replace("Z",""));
        return offsetUtc(to, zoneId);
    }

    public ZoneId makeZoneId(){
        return ZoneId.of(timeZone);
    }

    private LocalDateTime offsetUtc(LocalDateTime dateTime, ZoneId zoneId) {
        final ZoneId UTC = ZoneId.of("UTC");
        return ZonedDateTime.of(dateTime, zoneId)
                .withZoneSameInstant(UTC)
                .toLocalDateTime();
    }
}
