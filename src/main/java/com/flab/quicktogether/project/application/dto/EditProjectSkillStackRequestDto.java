package com.flab.quicktogether.project.application.dto;

import com.flab.quicktogether.common.SkillStack;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditProjectSkillStackRequestDto {
    private SkillStack skillStack;
}
