package com.flab.quicktogether.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static com.flab.quicktogether.project.domain.InviteStatus.*;

@Getter
@Entity
public class Invite {

    @Id
    @GeneratedValue
    @Column(name = "Invite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Long requestMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Long invitedMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Long projectId;

    @Enumerated(EnumType.STRING)
    private InviteStatus inviteStatus;


    private Invite(Long projectId, Long requestMemberId, Long invitedMemberId) {
        this.projectId = projectId;
        this.requestMemberId = requestMemberId;
        this.invitedMemberId = invitedMemberId;
    }

    public static Invite inviteMember(Long projectId, Long requestMemberId, Long invitedMemberId){
        Invite invite = new Invite(projectId, requestMemberId, invitedMemberId);
        invite.inviteStatus = WAIT;
        return invite;
    }

    public void accept() {
        this.inviteStatus = ACCEPT;
    }

    public void reject() {
        this.inviteStatus = REJECT;
    }
}
