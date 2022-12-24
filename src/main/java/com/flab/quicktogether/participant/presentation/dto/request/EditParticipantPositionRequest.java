package com.flab.quicktogether.participant.presentation.dto.request;

import com.flab.quicktogether.globalsetting.domain.Position;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditParticipantPositionRequest {
    @NotNull
    private Position position;

}
