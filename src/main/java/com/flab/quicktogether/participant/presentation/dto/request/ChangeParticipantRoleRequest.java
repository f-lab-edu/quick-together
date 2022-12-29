package com.flab.quicktogether.participant.presentation.dto.request;

import com.flab.quicktogether.participant.application.dto.ChangeParticipantRoleRequestDto;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeParticipantRoleRequest {
    @NotNull
    private ParticipantRole participantRole;

    public ChangeParticipantRoleRequestDto toServiceDto() {
        return new ChangeParticipantRoleRequestDto(this.getParticipantRole());
    }
}
