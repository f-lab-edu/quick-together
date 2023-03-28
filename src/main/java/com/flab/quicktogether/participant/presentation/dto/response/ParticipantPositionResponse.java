package com.flab.quicktogether.participant.presentation.dto.response;

import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.common.Position;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantPositionResponse {

    @NotNull
    private Long memberId;

    @NotNull
    private Long projectId;

    @NotNull
    private Set<Position> positions = new HashSet<>();

    public ParticipantPositionResponse(Long projectId, Long memberId, Participant participant) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.positions = participant.getPositions();
    }

}
