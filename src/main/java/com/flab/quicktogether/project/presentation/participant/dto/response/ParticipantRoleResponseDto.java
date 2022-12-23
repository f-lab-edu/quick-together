package com.flab.quicktogether.project.presentation.participant.dto.response;

import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.ParticipantRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantRoleResponseDto {

    @NotNull
    private Long memberId;

    @NotNull
    private Long projectId;

    @NotNull
    private ParticipantRole participantRole;


    public ParticipantRoleResponseDto(Long projectId, Long memberId, Participant participant) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.participantRole = participant.getParticipantRole();
    }
}
