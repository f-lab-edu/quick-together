package com.flab.quicktogether.meeting.presentation.dto;

import com.flab.quicktogether.meeting.domain.MeetingInfo;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MeetingRequestDto {
    private String title;

    private String description;

    private SuggestionTimeDto suggestionTime;

    protected MeetingRequestDto() {
    }

    @Builder
    public MeetingRequestDto(String title, String description, SuggestionTimeDto suggestionTime) {
        this.title = title;
        this.description = description;
        this.suggestionTime = suggestionTime;
    }

    public MeetingInfo toMeetingInfo() {
        TimeBlock timeBlock = suggestionTime.toTimeBlock();
        return new MeetingInfo(title, description, timeBlock);
    }

}
