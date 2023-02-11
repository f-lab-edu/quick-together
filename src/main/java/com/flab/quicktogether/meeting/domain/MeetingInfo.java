package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
