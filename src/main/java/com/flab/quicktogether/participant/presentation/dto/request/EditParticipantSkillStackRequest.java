package com.flab.quicktogether.participant.presentation.dto.request;

import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.participant.application.dto.EditParticipantSkillStackRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditParticipantSkillStackRequest {
    @NotNull
    private SkillStack skillStack;


    public EditParticipantSkillStackRequestDto toServiceDto() {
        return new EditParticipantSkillStackRequestDto(this.getSkillStack());
    }
}
