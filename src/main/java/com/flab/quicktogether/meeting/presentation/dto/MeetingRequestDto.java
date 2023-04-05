package com.flab.quicktogether.meeting.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.meeting.domain.MeetingInfo;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class MeetingRequestDto {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("suggested_time")
    private suggestedTime suggestionTime;

    protected MeetingRequestDto() {
    }

    @Builder
    public MeetingRequestDto(String title, String description, suggestedTime suggestionTime) {
        this.title = title;
        this.description = description;
        this.suggestionTime = suggestionTime;
    }

    public MeetingInfo toMeetingInfo() {
        TimeBlock timeBlock = suggestionTime.toTimeBlock();
        return new MeetingInfo(title, description, timeBlock);
    }

}
