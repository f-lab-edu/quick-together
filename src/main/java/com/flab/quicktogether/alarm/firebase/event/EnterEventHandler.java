package com.flab.quicktogether.alarm.firebase.event;

import com.flab.quicktogether.alarm.firebase.FcmTokenNotFoundException;
import com.flab.quicktogether.alarm.firebase.NotificationMessageProvider;
import com.flab.quicktogether.alarm.firebase.FcmService;
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
    private final FcmService fcmService;
    private final FcmTokenRepository fcmTokenRepository;
    private final ParticipantRepository participantRepository;

    @EventListener(ProjectEnterEvent.class)
    @Async
    public void handle(ProjectEnterEvent event) {

        Long projectId = event.getProjectId();

        Participant participantAdmin = participantRepository.findAdminByProjectId(projectId)
                .orElseThrow(ParticipantNotFoundException::new);

        // 프로젝트 어드민이 로그인상태인 경우 알림을 보냄
        fcmTokenRepository.findByMemberId(participantAdmin.getMember().getId())
                .ifPresentOrElse(fcmToken -> {
                            fcmService.sendAlarm(NotificationMessageProvider.enterMember(fcmToken.getToken()));
                        },
                        () -> {
                            log.info("FCM Token Not FOUND");
                            throw new FcmTokenNotFoundException();
                        });
    }
}
