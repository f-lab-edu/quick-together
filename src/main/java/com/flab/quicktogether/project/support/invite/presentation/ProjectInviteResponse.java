package com.flab.quicktogether.project.support.invite.presentation;

import com.flab.quicktogether.project.support.invite.domain.Invite;
import com.flab.quicktogether.project.support.ProjectJoinStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectInviteResponse {

    @NotNull
    private Long projectId;

    @NotNull
    private Long requestMember;

    @NotNull
    private Long invitedMember;

    @NotNull
    private ProjectJoinStatus inviteStatus;

    public ProjectInviteResponse(Invite invite) {
        this.projectId = invite.getProject().getId();
        this.requestMember = invite.getRequestMember().getId();
        this.invitedMember = invite.getInvitedMember().getId();
        this.inviteStatus = invite.getInviteStatus();
    }
}
