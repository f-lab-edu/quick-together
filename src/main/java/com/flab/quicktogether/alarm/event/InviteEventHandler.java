package com.flab.quicktogether.alarm.event;

import com.flab.quicktogether.alarm.message.AlarmMessage;
import com.flab.quicktogether.alarm.message.SimpleMessageDto;
import com.flab.quicktogether.alarm.service.AlarmSendService;
import com.flab.quicktogether.alarm.service.AlarmTokenService;
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
    private final AlarmSendService<SimpleMessageDto> alarmSendService;
    private final AlarmTokenService alarmTokenService;
    private final AlarmMessage inviteAlarmMessage;

    @EventListener(ProjectInviteEvent.class)
    @Async
    public void handle(ProjectInviteEvent event) {

        Long invitedMemberId = event.getInvitedMemberId();

        // 초대되어진 멤버에게 알림을 보냄
        String token = alarmTokenService.getToken(invitedMemberId);
        alarmSendService.sendAlarm(token, inviteAlarmMessage);

    }
}
