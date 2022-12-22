package com.flab.quicktogether.project.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantPosition {

    @Id
    @GeneratedValue
    @Column(name = "participantPosition_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Enumerated(EnumType.STRING)
    private Position position;

    public ParticipantPosition(Participant participant, Position position) {
        this.participant = participant;
        this.position = position;
    }
}