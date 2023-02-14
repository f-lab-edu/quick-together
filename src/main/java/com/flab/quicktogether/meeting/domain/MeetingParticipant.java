package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.domain.ParticipantRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class MeetingParticipant {
    @Id
    @GeneratedValue
    @Column(name = "meeting_participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ParticipantRole participantRole;

    MeetingParticipant(Member member, ParticipantRole participantRole) {
        this.member = member;
        this.participantRole = participantRole;
    }

    public static MeetingParticipant assignProjectParticipant(Member host, Participant participant) {
        Member member = participant.getMember();
        ParticipantRole meetingParticipantRole = assignRole(host, participant);

        return new MeetingParticipant(member, meetingParticipantRole);
    }

    private static ParticipantRole assignRole(Member host, Participant participant) {
        Member member = participant.getMember();
        if (member.equals(host)) {
            return ParticipantRole.ROLE_ADMIN;
        }
        return participant.getParticipantRole();
    }

    void promote() {
        if (!this.participantRole.equals(ParticipantRole.ROLE_ADMIN)) {
            this.participantRole = ParticipantRole.ROLE_ADMIN;
        }
    }

    void demote() {
        if (!this.participantRole.equals(ParticipantRole.ROLE_USER)) {
            this.participantRole = ParticipantRole.ROLE_USER;
        }
    }

}
