package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.domain.SkillStack;
import lombok.Data;
import lombok.NonNull;


@Data
public class ParticipantSkillStackDto {

    @NonNull
    private SkillStack skillStack;


    public ParticipantSkillStackDto(@NonNull SkillStack skillStack) {
        this.skillStack = skillStack;
    }
}
