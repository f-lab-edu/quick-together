package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.domain.Position;
import lombok.Data;
import lombok.NonNull;


@Data
public class EditParticipantPositionDto {


    @NonNull
    private Long memberId;

    @NonNull
    private Long projectId;

    @NonNull
    private Position position;


}
