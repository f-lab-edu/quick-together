package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Participant {

    @Id
    @GeneratedValue
    @Column(name = "participants_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ParticipantRole participantRole;

    public Participant(Member member, Project project, ParticipantRole participantRole) {
        this.member = member;
        this.project = project;
        this.participantRole = participantRole;
    }

    public static Participant addMember(Member member, Project project, ParticipantRole participantRole){
        return new Participant(member, project, ParticipantRole.ROLE_USER);
    }

    public void changeParticipantRole(ParticipantRole editParticipantRole){
        this.participantRole = editParticipantRole;
    }
}
