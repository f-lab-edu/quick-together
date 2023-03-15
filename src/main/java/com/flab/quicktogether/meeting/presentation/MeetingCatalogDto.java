package com.flab.quicktogether.meeting.presentation;

import com.flab.quicktogether.meeting.domain.Meeting;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingCatalogDto {
    private final Long id;
    private final String projectName;
    private final String title;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public static MeetingCatalogDto from(Meeting meeting){
        return MeetingCatalogDto.builder()
                .id(meeting.getId())
                .projectName(meeting.getProject().getProjectName())
                .title(meeting.getTitle())
                .startDateTime(meeting.getTimeBlock().getStartDateTime())
                .endDateTime(meeting.getTimeBlock().getEndDateTime())
                .build();
    }
}
