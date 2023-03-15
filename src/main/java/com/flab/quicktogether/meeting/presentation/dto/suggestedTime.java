package com.flab.quicktogether.meeting.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@ToString
@EqualsAndHashCode
public class suggestedTime {

    @JsonProperty("start_date_time")
    private LocalDateTime startDateTime;

    @JsonProperty("end_date_time")
    private LocalDateTime endDateTime;

    @JsonProperty("time_zone")
    private String timeZone;

    @Builder
    public suggestedTime(LocalDateTime startDateTime, LocalDateTime endDateTime, String timeZone) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.timeZone = timeZone;
    }

    public TimeBlock toTimeBlock() {
        ZoneId zoneId = ZoneId.of(timeZone);
        return TimeBlock.asCommonTime(startDateTime, endDateTime, zoneId);
    }
}
