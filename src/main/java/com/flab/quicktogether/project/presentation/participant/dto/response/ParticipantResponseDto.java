package com.flab.quicktogether.project.presentation.participant.dto.response;

import com.flab.quicktogether.project.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class ParticipantResponseDto {

    @NonNull
    private Long MemberId;

    @NonNull
    private String MemberName;

    @NonNull
    private Long projectId;

    @NonNull
    private String projectName;
    @NonNull
    @Enumerated(EnumType.STRING)
    private ParticipantRole participantRole;

    @NonNull
    private List<Position> positions = new ArrayList<>();

    @NonNull
    private List<SkillStack> skillStacks = new ArrayList<>();

    public ParticipantResponseDto(Participant participant) {
        this.MemberId = participant.getMember().getId();
        this.MemberName = participant.getMember().getMemberName();
        this.projectId = participant.getProject().getId();
        this.projectName = participant.getProject().getProjectName();
        this.participantRole = participant.getParticipantRole();
        this.positions = participant.getPositions();
        this.skillStacks = participant.getSkillStacks();
    }
}
