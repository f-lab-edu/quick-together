package com.flab.quicktogether.alarm.firebase.event;

import com.flab.quicktogether.alarm.firebase.NotificationMessageProvider;
import com.flab.quicktogether.alarm.firebase.FcmService;
import com.flab.quicktogether.member.infrastructure.FcmTokenRepository;
import com.flab.quicktogether.project.support.invite.event.ProjectInviteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InviteEventHandler {
    private final FcmService fcmService;
    private final FcmTokenRepository fcmTokenRepository;

    @EventListener(ProjectInviteEvent.class)
    public void handle(ProjectInviteEvent event) {

        Long invitedMemberId = event.getInvitedMemberId();

        // 초대되어진 멤버가 로그인상태인 경우 알림을 보냄
        fcmTokenRepository.findByMemberId(invitedMemberId)
                .ifPresentOrElse(fcmToken -> {
                            fcmService.sendAlarm(NotificationMessageProvider.inviteMember(fcmToken.getToken()));
                        },
                        () -> log.info("FCM Token Not FOUND"));
    }
}
