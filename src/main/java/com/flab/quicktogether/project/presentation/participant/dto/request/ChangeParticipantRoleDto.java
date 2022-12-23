package com.flab.quicktogether.project.presentation.participant.dto.request;

import com.flab.quicktogether.project.domain.ParticipantRole;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeParticipantRoleDto {
    @NotNull
    private ParticipantRole participantRole;
}
