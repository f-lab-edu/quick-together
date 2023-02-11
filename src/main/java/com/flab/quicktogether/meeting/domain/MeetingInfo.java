package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class MeetingInfo {
    private String title;

    private String description;

    private TimeBlock suggestionTime;

    public MeetingInfo(String title, String description, TimeBlock suggestionTime) {
        this.title = title;
        this.description = description;
        this.suggestionTime = suggestionTime;
    }
}
