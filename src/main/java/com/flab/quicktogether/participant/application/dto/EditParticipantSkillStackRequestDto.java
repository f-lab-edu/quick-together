package com.flab.quicktogether.participant.application.dto;

import com.flab.quicktogether.globalsetting.domain.SkillStack;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditParticipantSkillStackRequestDto {
    private SkillStack skillStack;
}
