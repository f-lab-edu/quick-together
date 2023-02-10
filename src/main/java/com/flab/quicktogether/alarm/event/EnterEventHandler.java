package com.flab.quicktogether.alarm.event;

import com.flab.quicktogether.alarm.message.NotificationContentProvider;
import com.flab.quicktogether.alarm.service.AlarmSendService;
import com.flab.quicktogether.alarm.service.AlarmTokenService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.exception.ParticipantNotFoundException;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.support.enter.event.ProjectEnterEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnterEventHandler {
    private final AlarmSendService alarmSendService;
    private final AlarmTokenService alarmTokenService;
    private final ParticipantRepository participantRepository;
    private final NotificationContentProvider notificationContentProvider;

    @EventListener(ProjectEnterEvent.class)
    @Async
    public void handle(ProjectEnterEvent event) {

        Long projectId = event.getProjectId();

        Participant participantAdmin = participantRepository.findAdminByProjectId(projectId)
                .orElseThrow(ParticipantNotFoundException::new);

        // 프로젝트 어드민에게 알림을 보냄
        String token = alarmTokenService.getToken(participantAdmin.getMember().getId());
        alarmSendService.sendAlarm(token, notificationContentProvider.enterMember());

    }
}
