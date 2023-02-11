package com.flab.quicktogether.meeting.domain;

import com.flab.quicktogether.meeting.domain.exception.AlreadyApprovedMeetingException;
import com.flab.quicktogether.meeting.domain.exception.AlreadyDeniedMeetingException;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class MeetingProposal {

    @Id
    @GeneratedValue
    @Column(name = "meeting_proposal_id")
    private Long id;

    @Embedded
    private MeetingInfo meetingInfo;

    @Enumerated(EnumType.STRING)
    private MeetingProposalStatus status;

    MeetingProposal(MeetingInfo meetingInfo) {
        this.status = MeetingProposalStatus.REQUESTED;
    }

    void approve() {
        verifyApproval();
        this.status = MeetingProposalStatus.APPROVED;
    }

    void deny() {
        verifyDenial();
        this.status = MeetingProposalStatus.DENIED;
    }

    private void verifyApproval() {
        if (this.status.equals(MeetingProposalStatus.APPROVED)) {
            throw new AlreadyApprovedMeetingException();
        }
    }
    private void verifyDenial() {
        if (this.status.equals(MeetingProposalStatus.APPROVED)) {
            throw new AlreadyDeniedMeetingException();
        }
    }
}
