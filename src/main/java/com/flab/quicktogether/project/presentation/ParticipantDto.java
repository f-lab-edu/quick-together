package com.flab.quicktogether.project.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Data
@AllArgsConstructor
public class ParticipantDto {

    @NonNull
    private Long memberId;

    @NonNull
    private Long projectId;

}
