package com.flab.quicktogether.participant.presentation.dto.response;

import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.globalsetting.domain.Position;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantPositionResponse {

    @NotNull
    private Long memberId;

    @NotNull
    private Long projectId;

    @NotNull
    private List<Position> positions = new ArrayList<>();

    public ParticipantPositionResponse(Long projectId, Long memberId, Participant participant) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.positions = participant.getPositions();
    }

}
