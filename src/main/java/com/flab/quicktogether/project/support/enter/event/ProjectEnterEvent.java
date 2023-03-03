package com.flab.quicktogether.project.support.enter.event;

import com.flab.quicktogether.project.event.Event;
import lombok.Getter;

@Getter
public class ProjectEnterEvent extends Event {
    private long projectId;
    private long enteredMemberId;

    public ProjectEnterEvent(long projectId, long enteredMemberId) {
        super();
        this.projectId = projectId;
        this.enteredMemberId = enteredMemberId;
    }
}
