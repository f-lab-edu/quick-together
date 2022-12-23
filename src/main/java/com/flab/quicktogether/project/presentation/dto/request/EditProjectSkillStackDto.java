package com.flab.quicktogether.project.presentation.dto.request;

import com.flab.quicktogether.project.domain.SkillStack;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditProjectSkillStackDto {
    @NonNull
    private SkillStack skillStack;

}
