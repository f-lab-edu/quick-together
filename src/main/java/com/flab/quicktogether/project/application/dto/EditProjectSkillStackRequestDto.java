package com.flab.quicktogether.project.application.dto;

import com.flab.quicktogether.globalsetting.domain.SkillStack;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditProjectSkillStackRequestDto {
    private SkillStack skillStack;
}
