package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.domain.SkillStack;
import lombok.Data;
import lombok.NonNull;


@Data
public class EditParticipantSkillStackDto {
    @NonNull
    private Long memberId;

    @NonNull
    private Long projectId;
    @NonNull
    private SkillStack skillStack;


}
