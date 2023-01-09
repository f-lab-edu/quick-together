package com.flab.quicktogether.participant.presentation.dto.response;

import com.flab.quicktogether.globalsetting.domain.Position;
import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


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
    private List<Position> positions = new ArrayList<>();

    @NotNull
    private List<SkillStack> skillStacks = new ArrayList<>();

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
