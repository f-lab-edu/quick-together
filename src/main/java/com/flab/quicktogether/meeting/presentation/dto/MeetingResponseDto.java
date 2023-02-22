package com.flab.quicktogether.meeting.presentation.dto;

import com.flab.quicktogether.meeting.domain.Meeting;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@RequiredArgsConstructor
public class MeetingResponseDto {
    private final Long id;
    private final String title;
    private final String description;
    private final List<MeetingParticipantDto> meetingParticipantDto;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private List<Link> links;


    public static MeetingResponseDto from(Meeting meeting, String timezone) {
        ZoneId zoneId = ZoneId.of(timezone);
        TimeBlock timeBlock = meeting.getTimeBlock();

        LocalDateTime localStartTime = offsetTimezone(timeBlock.getStartDateTime(), zoneId);
        LocalDateTime localEndTime = offsetTimezone(timeBlock.getEndDateTime(), zoneId);

        List<Link> links = new ArrayList<>();

// REST API 원칙중 하나인 HATEOS를 통해 Meeting조회시 활용할수있는 기능을 path로 뽑아내고자 하는데 이렇게 노가다방식으로 해결하는것보다 좋은방법이 없을지 고민중입니다.
// 동의, 거부, 수정, 수정요청과 같은 버튼은 이런식으로 구현하는것이 좋은지 방향성이 궁금합니다.
//        Link.of("accept", );
//        Link.of("deny", );
//        Link.of("edit",);
//        Link.of("request-edit",);
//        Link.of("accept-edit")
//        Link.of("promote", );
//        Link.of("demote",);
//        Link.of("ban",);
//        Link.of("join",);

        List<MeetingParticipantDto> meetingParticipantDtos = meeting.getMeetingParticipants().getList()
                .stream()
                .map(participant -> MeetingParticipantDto.from(participant))
                .toList();

        return MeetingResponseDto.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .startDateTime(localStartTime)
                .endDateTime(localEndTime)
                .links(links)
                .meetingParticipantDto(meetingParticipantDtos)
                .build();
    }

    private static LocalDateTime offsetTimezone(LocalDateTime dateTime, ZoneId zoneId) {
        ZoneId utc = ZoneId.of("UTC");

        return ZonedDateTime.of(dateTime, utc)
                .withZoneSameInstant(zoneId)
                .toLocalDateTime();
    }

}
