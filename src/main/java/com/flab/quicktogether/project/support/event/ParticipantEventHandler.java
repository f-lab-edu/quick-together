package com.flab.quicktogether.project.support.event;

import com.flab.quicktogether.participant.application.ParticipantService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ParticipantEventHandler {
    private ParticipantService participantService;

    public ParticipantEventHandler(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @EventListener(ProjectJoinEvent.class)
    public void handle(ProjectJoinEvent event) {
        participantService.joinProject(event.getProjectId(), event.getInvitedMemberId());
    }
}
