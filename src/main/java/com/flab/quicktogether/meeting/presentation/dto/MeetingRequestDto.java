package com.flab.quicktogether.meeting.presentation.dto;

import com.flab.quicktogether.meeting.domain.MeetingInfo;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Getter;

@Getter
public class MeetingRequestDto {
    private String title;

    private String description;

    private SuggestionTimeDto suggestionTime;


    public MeetingInfo toMeetingInfo() {
        TimeBlock timeBlock = suggestionTime.toTimeBlock();
        return new MeetingInfo(title, description, timeBlock);
    }

}
