package com.flab.quicktogether.meeting.presentation.dto;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SuggestionTimeDto {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String timeZone;

    @Builder
    public SuggestionTimeDto(LocalDateTime startDateTime, LocalDateTime endDateTime, String timeZone) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.timeZone = timeZone;
    }

    public TimeBlock toTimeBlock() {
        ZoneId zoneId = ZoneId.of(timeZone);
        return TimeBlock.asCommonTime(startDateTime, endDateTime, zoneId);
    }
}
