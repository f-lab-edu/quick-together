package com.flab.quicktogether.project.support.search.presentation.dto;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import lombok.Data;

import java.util.Set;

@Data
public class ParticipantInfoDto {
    String memberName;
    Set<SkillStack> skillStacks;
    Set<Position> positions;

    public ParticipantInfoDto(String memberName, Set<SkillStack> skillStacks, Set<Position> positions) {
        this.memberName = memberName;
        this.skillStacks = skillStacks;
        this.positions = positions;
    }
}
