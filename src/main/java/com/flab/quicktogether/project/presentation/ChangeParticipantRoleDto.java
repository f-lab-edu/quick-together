package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.domain.ParticipantRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ChangeParticipantRoleDto {

    @NonNull
    private Long memberId;

    @NonNull
    private Long projectId;

    @NonNull
    private ParticipantRole participantRole;

}
