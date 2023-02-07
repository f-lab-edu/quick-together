package com.flab.quicktogether.alarm.event;

import com.flab.quicktogether.alarm.firebase.AlarmTokenNotFoundException;
import com.flab.quicktogether.alarm.firebase.NotificationMessageProvider;
import com.flab.quicktogether.alarm.service.AlarmTokenService;
import com.flab.quicktogether.alarm.service.FcmAlarmSendService;
import com.flab.quicktogether.member.infrastructure.FcmTokenRepository;
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
    private final FcmAlarmSendService fcmService;
    private final AlarmTokenService alarmTokenService;
    private final ParticipantRepository participantRepository;

    @EventListener(ProjectEnterEvent.class)
    @Async
    public void handle(ProjectEnterEvent event) {

        Long projectId = event.getProjectId();

        Participant participantAdmin = participantRepository.findAdminByProjectId(projectId)
                .orElseThrow(ParticipantNotFoundException::new);

        // 프로젝트 어드민에게 알림을 보냄
        String token = alarmTokenService.getToken(participantAdmin.getMember().getId());
        fcmService.sendAlarm(NotificationMessageProvider.enterMember(token));

    }
}
