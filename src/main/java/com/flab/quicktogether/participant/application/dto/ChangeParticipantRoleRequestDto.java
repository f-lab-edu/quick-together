package com.flab.quicktogether.participant.application.dto;

import com.flab.quicktogether.participant.domain.ParticipantRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeParticipantRoleRequestDto {
    private ParticipantRole participantRole;
}
