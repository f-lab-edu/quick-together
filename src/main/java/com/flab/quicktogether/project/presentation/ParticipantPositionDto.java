package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.domain.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Data
public class ParticipantPositionDto {

    @NonNull
    private Position position;


    public ParticipantPositionDto(@NonNull Position position) {
        this.position = position;
    }
}
