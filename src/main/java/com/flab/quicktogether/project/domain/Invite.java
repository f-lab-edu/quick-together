package com.flab.quicktogether.project.domain;

import com.flab.quicktogether.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.flab.quicktogether.project.domain.InviteStatus.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite {

    @Id
    @GeneratedValue
    @Column(name = "invite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestMember_id")
    private Member requestMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitedMember_id")
    private Member invitedMember;

    @Enumerated(EnumType.STRING)
    private InviteStatus inviteStatus;


    public Invite(Project project, Member requestMember, Member invitedMember) {
        this.project = project;
        this.requestMember = requestMember;
        this.invitedMember = invitedMember;
    }

    public static Invite inviteMember(Project project, Member requestMember, Member invitedMember){
        Invite invite = new Invite(project, requestMember, invitedMember);
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
