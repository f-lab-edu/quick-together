package com.flab.quicktogether.meeting.presentation.dto;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SuggestionTimeDto {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String timezone;

    public TimeBlock toTimeBlock() {
        ZoneId zoneId = ZoneId.of(timezone);
        return TimeBlock.asCommonTime(startDateTime, endDateTime, zoneId);
    }
}
