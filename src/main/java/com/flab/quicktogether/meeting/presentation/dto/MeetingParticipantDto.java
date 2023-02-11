package com.flab.quicktogether.meeting.presentation.dto;

import com.flab.quicktogether.meeting.domain.MeetingParticipant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingParticipantDto {
    private Long meetingMemberId;
    private String name;
    private ParticipantRole participantRole;

    private MeetingParticipantDto(Long meetingMemberId, String name, ParticipantRole participantRole) {
        this.meetingMemberId = meetingMemberId;
        this.name = name;
        this.participantRole = participantRole;
    }

    public static MeetingParticipantDto from(MeetingParticipant meetingParticipant) {
        Long id = meetingParticipant.getId();
        String name = meetingParticipant.getMember().getName();
        ParticipantRole participantRole = meetingParticipant.getParticipantRole();
        return new MeetingParticipantDto(id, name, participantRole);
    }
}
