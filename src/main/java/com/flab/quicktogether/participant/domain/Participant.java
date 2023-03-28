package com.flab.quicktogether.participant.domain;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.participant.exception.NotAuthorizedParticipantException;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.common.SkillStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static com.flab.quicktogether.participant.domain.ParticipantRole.*;
import static com.flab.quicktogether.participant.domain.ParticipantRole.ROLE_ADMIN;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ParticipantPosition", joinColumns = @JoinColumn(name = "participant_id"))
    @Enumerated(EnumType.STRING)
    private Set<Position> positions = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ParticipantSkillStack", joinColumns = @JoinColumn(name = "participant_id"))
    @Enumerated(EnumType.STRING)
    private Set<SkillStack> skillStacks = new HashSet<>();


    public Participant(Member member, Project project, ParticipantRole participantRole) {
        this.member = member;
        this.project = project;
        this.participantRole = participantRole;
    }

    public static Participant addParticipant(Project project, Member member){
        return new Participant(member, project, ROLE_USER);
    }

    public static Participant registerFounder(Project project, Member founder) {
        return new Participant(founder, project, ParticipantRole.ROLE_ADMIN);
    }

    public void checkPermission(){
        if(this.participantRole.equals(ROLE_ADMIN)){
            //do nothing
        }else {
            throw new NotAuthorizedParticipantException();
        }
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
