package com.flab.quicktogether.meeting.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.quicktogether.meeting.domain.Meeting;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@Builder
@RequiredArgsConstructor
public class MeetingCatalogDto {
    private final Long id;
    private final String title;
    private final String projectName;

    @JsonProperty("start_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private final LocalDateTime startDateTime;

    @JsonProperty("end_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private final LocalDateTime endDateTime;

    public static MeetingCatalogDto from(Meeting meeting, String timezone) {
        ZoneId zoneId = ZoneId.of(timezone);
        TimeBlock timeBlock = meeting.getTimeBlock();

        LocalDateTime localStartTime = offsetTimezone(timeBlock.getStartDateTime(), zoneId);
        LocalDateTime localEndTime = offsetTimezone(timeBlock.getEndDateTime(), zoneId);

        List<MeetingParticipantDto> meetingParticipantDtos = meeting.getMeetingParticipants().getList()
                .stream()
                .map(participant -> MeetingParticipantDto.from(participant))
                .toList();

        return MeetingCatalogDto.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .startDateTime(localStartTime)
                .endDateTime(localEndTime)
                .build();
    }

    private static LocalDateTime offsetTimezone(LocalDateTime dateTime, ZoneId zoneId) {
        ZoneId utc = ZoneId.of("UTC");

        return ZonedDateTime.of(dateTime, utc)
                .withZoneSameInstant(zoneId)
                .toLocalDateTime();
    }

}
