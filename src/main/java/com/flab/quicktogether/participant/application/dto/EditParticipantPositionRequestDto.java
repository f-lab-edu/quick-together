package com.flab.quicktogether.participant.application.dto;

import com.flab.quicktogether.common.Position;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditParticipantPositionRequestDto {
    private Position position;
}
