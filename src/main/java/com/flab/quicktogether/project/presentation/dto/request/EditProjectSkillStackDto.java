package com.flab.quicktogether.project.presentation.dto.request;

import com.flab.quicktogether.project.domain.SkillStack;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditProjectSkillStackDto {
    @NotNull
    private SkillStack skillStack;
}
