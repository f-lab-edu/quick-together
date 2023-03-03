package com.flab.quicktogether.project.support.invite.event;

import com.flab.quicktogether.project.event.Event;
import lombok.Getter;

@Getter
public class ProjectInviteEvent extends Event {
    private long projectId;
    private long invitedMemberId;

    public ProjectInviteEvent(long projectId, long invitedMemberId) {
        super();
        this.projectId = projectId;
        this.invitedMemberId = invitedMemberId;
    }
}
