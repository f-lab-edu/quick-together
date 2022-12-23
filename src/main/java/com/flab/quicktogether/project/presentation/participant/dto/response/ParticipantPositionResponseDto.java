package com.flab.quicktogether.project.presentation.participant.dto.response;

import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.Position;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantPositionResponseDto {

    @NonNull
    private Long memberId;

    @NonNull
    private Long projectId;

    @NonNull
    private List<Position> positions = new ArrayList<>();

    public ParticipantPositionResponseDto(Long projectId, Long memberId, Participant participant) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.positions = participant.getPositions();
    }

}
