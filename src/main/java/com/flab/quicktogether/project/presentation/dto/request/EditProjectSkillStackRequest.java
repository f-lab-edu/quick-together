package com.flab.quicktogether.project.presentation.dto.request;

import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.project.application.dto.EditProjectSkillStackRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditProjectSkillStackRequest {
    @NotNull
    private SkillStack skillStack;

    public EditProjectSkillStackRequestDto toServiceDto() {
        return new EditProjectSkillStackRequestDto(this.getSkillStack());
    }
}
