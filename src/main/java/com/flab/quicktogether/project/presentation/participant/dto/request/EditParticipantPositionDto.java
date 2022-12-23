package com.flab.quicktogether.project.presentation.participant.dto.request;

import com.flab.quicktogether.project.domain.Position;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditParticipantPositionDto {
    @NotNull
    private Position position;

}
