package com.flab.quicktogether.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ParticipantSkillStack {

    @Id
    @GeneratedValue
    @Column(name = "participantSkill_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Enumerated(EnumType.STRING)
    private SkillStack skillStack;

    public ParticipantSkillStack(Participant participant, SkillStack skillStack) {
        this.participant = participant;
        this.skillStack = skillStack;
    }
}
