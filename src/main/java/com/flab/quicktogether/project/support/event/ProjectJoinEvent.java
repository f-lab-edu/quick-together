package com.flab.quicktogether.project.support.event;

import com.flab.quicktogether.project.event.Event;
import lombok.Getter;

@Getter
public class ProjectJoinEvent extends Event {
    private long projectId;
    private long invitedMemberId;

    public ProjectJoinEvent(long projectId, long invitedMemberId) {
        super();
        this.projectId = projectId;
        this.invitedMemberId = invitedMemberId;
    }
}
