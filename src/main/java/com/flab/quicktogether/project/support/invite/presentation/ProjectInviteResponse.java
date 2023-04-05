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
    private String projectName;
    @NotNull
    private Long requestMemberId;

    @NotNull
    private String requestMemberName;

    @NotNull
    private Long invitedMemberId;

    @NotNull
    private String invitedMemberName;

    @NotNull
    private ProjectJoinStatus inviteStatus;

    public ProjectInviteResponse(Invite invite) {
        this.projectId = invite.getProject().getId();
        this.projectName = invite.getProject().getProjectName();
        this.requestMemberId = invite.getRequestMember().getId();
        this.requestMemberName = invite.getRequestMember().getMemberName();
        this.invitedMemberId = invite.getInvitedMember().getId();
        this.invitedMemberName = invite.getInvitedMember().getMemberName();
        this.inviteStatus = invite.getInviteStatus();
    }
}
