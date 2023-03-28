package com.flab.quicktogether.participant.presentation.dto.response;

import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.common.SkillStack;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantSkillStackResponse {

    @NotNull
    private Long memberId;

    @NotNull
    private Long projectId;

    @NotNull
    private Set<SkillStack> skillStacks = new HashSet<>();

    public ParticipantSkillStackResponse(Long projectId, Long memberId, Participant participant) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.skillStacks = participant.getSkillStacks();
    }

}
