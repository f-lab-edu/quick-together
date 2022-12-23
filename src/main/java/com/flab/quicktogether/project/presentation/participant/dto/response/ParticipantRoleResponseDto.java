package com.flab.quicktogether.project.presentation.participant.dto.response;

import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.ParticipantRole;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantRoleResponseDto {

    @NonNull
    private Long memberId;

    @NonNull
    private Long projectId;

    @NonNull
    private ParticipantRole participantRole;


    public ParticipantRoleResponseDto(Long projectId, Long memberId, Participant participant) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.participantRole = participant.getParticipantRole();
    }
}
