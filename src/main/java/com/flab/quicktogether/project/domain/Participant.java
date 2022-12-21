package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Participant {

    @Id
    @GeneratedValue
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ParticipantRole participantRole;

    @ElementCollection
    @CollectionTable(name = "ParticipantPosition", joinColumns = @JoinColumn(name = "participant_id"))
    @Enumerated(EnumType.STRING)
    private List<Position> positions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "ParticipantSkillStack", joinColumns = @JoinColumn(name = "participant_id"))
    @Enumerated(EnumType.STRING)
    private List<SkillStack> skillStacks = new ArrayList<>();

    public Participant(Member member, Project project, ParticipantRole participantRole) {
        this.member = member;
        this.project = project;
        this.participantRole = participantRole;
    }

    public static Participant addMember(Project project, Member member){
        return new Participant(member, project, ParticipantRole.ROLE_USER);
    }

    public void changeParticipantRole(ParticipantRole editParticipantRole){
        this.participantRole = editParticipantRole;
    }

    public void addPosition(Position position){
        this.positions.add(position);
    }

    public void addSkillStack(SkillStack skillStack){
        this.skillStacks.add(skillStack);
    }

    public void removePosition(Position position){
        this.positions.remove(position);
    }

    public void removeSkillStack(SkillStack skillStack){
        this.skillStacks.remove(skillStack);
    }


}
