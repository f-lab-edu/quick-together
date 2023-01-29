package com.flab.quicktogether.alarm.firebase.event;

import com.flab.quicktogether.alarm.firebase.NotificationMessageProvider;
import com.flab.quicktogether.alarm.firebase.FcmService;
import com.flab.quicktogether.member.infrastructure.FcmTokenRepository;
import com.flab.quicktogether.project.support.enter.event.ProjectEnterEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnterEventHandler {
    private final FcmService fcmService;
    private final FcmTokenRepository fcmTokenRepository;

    @EventListener(ProjectEnterEvent.class)
    public void handle(ProjectEnterEvent event) {

        Long enteredMemberId = event.getEnteredMemberId();

        // 프로젝트 어드민이 로그인상태인 경우 알림을 보냄
        fcmTokenRepository.findByMemberId(enteredMemberId)
                .ifPresentOrElse(fcmToken -> {
                            fcmService.sendAlarm(NotificationMessageProvider.enterMember(fcmToken.getToken()));
                        },
                        () -> log.info("FCM Token Not FOUND"));
    }
}
