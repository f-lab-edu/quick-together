package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.meeting.domain.exception.MeetingParticipantNotfoundException;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import com.flab.quicktogether.participant.domain.Participants;
import com.flab.quicktogether.participant.exception.NotAuthorizedParticipantException;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingParticipants {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "meeting_id")
    private List<MeetingParticipant> meetingParticipantList = new ArrayList<>();

    public List<MeetingParticipant> getList() {
        return Collections.unmodifiableList(meetingParticipantList);
    }

    private MeetingParticipants(List<MeetingParticipant> meetingParticipants) {
        this.meetingParticipantList = meetingParticipants;
    }

    public static MeetingParticipants from(Member host, Participants participants) {
        List<MeetingParticipant> meetingParticipants = participants.participantsInfo().stream()
                .map(participant -> MeetingParticipant.assignProjectParticipant(host, participant))
                .collect(Collectors.toList());
        return new MeetingParticipants(meetingParticipants);
    }

    void assignParticipant(Member member, ParticipantRole authority) {
        MeetingParticipant meetingParticipant = new MeetingParticipant(member, authority);
        meetingParticipantList.add(meetingParticipant);
    }

    void assignParticipants(List<MeetingParticipant> meetingParticipants) {
        meetingParticipantList.addAll(meetingParticipants);
    }

    void checkAdmin(Long memberId) {
        meetingParticipantList.stream()
                .filter(participant -> participant
                        .getParticipantRole().equals(ParticipantRole.ROLE_ADMIN))
                .filter(admin -> admin.getMember()
                        .getId().equals(memberId))
                .findFirst()
                .orElseThrow(NotAuthorizedParticipantException::new);
    }

    MeetingParticipant find(Long meetingParticipantId) {
        return this.meetingParticipantList.stream()
                .filter(meetingParticipant -> meetingParticipant.getId().equals(meetingParticipantId))
                .findFirst()
                .orElseThrow(ParticipantNotFoundException::new);
    }
    void ban(Long toBeDeletedParticipantId) {
        this.meetingParticipantList.remove(toBeDeletedParticipantId);
    }

    void exit(Long memberId) {
        MeetingParticipant exitMember = findByMemberId(memberId);
        this.meetingParticipantList.remove(exitMember);
    }

    private MeetingParticipant findByMemberId(Long memberId) {
        checkParticipant(memberId);
        return this.meetingParticipantList.stream()
                .filter(meetingParticipant -> meetingParticipant.getMember().getId().equals(meetingParticipant))
                .findFirst()
                .orElseThrow(ParticipantNotFoundException::new);
    }

    void checkParticipant(Long memberId) {
        this.meetingParticipantList.stream()
                .filter(participant -> participant.getMember().getId().equals(memberId))
                .findFirst()
                .orElseThrow(MeetingParticipantNotfoundException::new);
    }
}
