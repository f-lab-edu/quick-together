package com.flab.quicktogether.project.presentation.participant.dto.request;

import com.flab.quicktogether.project.domain.SkillStack;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditParticipantSkillStackDto {
    @NonNull
    private SkillStack skillStack;


}
