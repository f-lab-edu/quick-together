package com.flab.quicktogether.participant.presentation.dto.response;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
public class ParticipantResponse {

    @NotNull
    private Long MemberId;

    @NotNull
    private String MemberName;

    @NotNull
    private Long projectId;

    @NotNull
    private String projectName;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ParticipantRole participantRole;

    @NotNull
    private Set<Position> positions = new HashSet<>();

    @NotNull
    private Set<SkillStack> skillStacks = new HashSet<>();

    public ParticipantResponse(Participant participant) {
        this.MemberId = participant.getMember().getId();
        this.MemberName = participant.getMember().getMemberName();
        this.projectId = participant.getProject().getId();
        this.projectName = participant.getProject().getProjectName();
        this.participantRole = participant.getParticipantRole();
        this.positions = participant.getPositions();
        this.skillStacks = participant.getSkillStacks();
    }
}
