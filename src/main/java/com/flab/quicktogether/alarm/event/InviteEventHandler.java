package com.flab.quicktogether.alarm.event;

import com.flab.quicktogether.alarm.firebase.AlarmTokenNotFoundException;
import com.flab.quicktogether.alarm.firebase.NotificationMessageProvider;
import com.flab.quicktogether.alarm.service.AlarmTokenService;
import com.flab.quicktogether.alarm.service.FcmAlarmSendService;
import com.flab.quicktogether.member.infrastructure.FcmTokenRepository;
import com.flab.quicktogether.project.support.invite.event.ProjectInviteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InviteEventHandler {
    private final FcmAlarmSendService fcmService;
    private final AlarmTokenService alarmTokenService;

    @EventListener(ProjectInviteEvent.class)
    @Async
    public void handle(ProjectInviteEvent event) {

        Long invitedMemberId = event.getInvitedMemberId();

        // 초대되어진 멤버에게 알림을 보냄
        String token = alarmTokenService.getToken(invitedMemberId);
        fcmService.sendAlarm(NotificationMessageProvider.inviteMember(token));

    }
}
