package com.flab.quicktogether.participant.presentation.dto.request;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.participant.application.dto.EditParticipantPositionRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditParticipantPositionRequest {
    @NotNull
    private Position position;

    public EditParticipantPositionRequestDto toServiceDto() {
        return new EditParticipantPositionRequestDto(this.getPosition());
    }
}
