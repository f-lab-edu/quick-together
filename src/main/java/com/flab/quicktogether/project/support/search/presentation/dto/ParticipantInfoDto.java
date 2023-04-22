package com.flab.quicktogether.project.support.search.presentation.dto;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import lombok.Data;

import java.util.Set;

@Data
public class ParticipantInfoDto {
    private String memberName;
    private ParticipantRole participantRole;
    private Set<SkillStack> skillStacks;
    private Set<Position> positions;

    public ParticipantInfoDto(String memberName, ParticipantRole participantRole, Set<SkillStack> skillStacks, Set<Position> positions) {
        this.memberName = memberName;
        this.participantRole = participantRole;
        this.skillStacks = skillStacks;
        this.positions = positions;
    }
}
