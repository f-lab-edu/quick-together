package com.flab.quicktogether.participant.presentation.dto.response;

import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantRoleResponse {

    @NotNull
    private Long memberId;

    @NotNull
    private Long projectId;

    @NotNull
    private ParticipantRole participantRole;


    public ParticipantRoleResponse(Long projectId, Long memberId, Participant participant) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.participantRole = participant.getParticipantRole();
    }
}
