package com.flab.quicktogether.project.presentation.participant.dto.response;

import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.SkillStack;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantSkillStackResponseDto {

    @NonNull
    private Long memberId;

    @NonNull
    private Long projectId;

    @NonNull
    private List<SkillStack> skillStacks = new ArrayList<>();

    public ParticipantSkillStackResponseDto(Long projectId, Long memberId, Participant participant) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.skillStacks = participant.getSkillStacks();
    }

}
