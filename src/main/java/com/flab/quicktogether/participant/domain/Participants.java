package com.flab.quicktogether.participant.domain;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.DuplicateProjectParticipationException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Embeddable
public class Participants {
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Participant> participants = new ArrayList<>();

    public Participants() {
    }

    public void isParticipantNot(Long invitedMemberId) {
        participants.stream()
                .filter(participant -> participant.getMember().getId().equals(invitedMemberId))
                .findFirst()
                .ifPresent(joinedParticipant -> {
                    throw new DuplicateProjectParticipationException();
                });
    }

    public void isAdmin(Long requestMemberId) {
        participants.stream()
                .filter(participant -> participant.getMember().getId().equals(requestMemberId))
                .findFirst()
                .ifPresentOrElse(Participant::checkPermission,
                        () -> {
                            throw new ParticipantNotFoundException();
                        });
    }

    public void addParticipant(Project project, Member member) {
        isParticipantNot(member.getId());
        participants.add(Participant.addParticipant(project, member));
    }

    public void registerFounder(Project project, Member fonder) {
        participants.add(Participant.registerFounder(project, fonder));
    }

    public Participant findParticipant(Long findMemberId) {
        return participants.stream()
                .filter(participant -> participant.getMember().getId().equals(findMemberId))
                .findFirst()
                .orElseThrow(ParticipantNotFoundException::new);
    }

}
