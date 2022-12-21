package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.member.domain.Member;
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
    private Member member;

    @NonNull
    private Project project;

    @NonNull
    private ParticipantRole participantRole;

    @NonNull
    private List<Position> positions = new ArrayList<>();

    @NonNull
    private List<SkillStack> skillStacks = new ArrayList<>();

    public ParticipantResponseDto(Participant participant) {
        this.member = participant.getMember();
        this.project = participant.getProject();
        this.participantRole = participant.getParticipantRole();
        this.positions = participant.getPositions();
        this.skillStacks = participant.getSkillStacks();
    }
}
