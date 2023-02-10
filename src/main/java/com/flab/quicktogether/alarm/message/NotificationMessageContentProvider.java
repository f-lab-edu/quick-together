package com.flab.quicktogether.alarm.message;

import org.springframework.stereotype.Component;

@Component
public class NotificationMessageContentProvider implements NotificationContentProvider<NotificationMessage> {
    public NotificationMessage inviteMember(){

        final String title = "초대장";
        final String body = "프로젝트에 초대되었습니다.";

        return new NotificationMessage(title, body);
    }

    public NotificationMessage enterMember(){

        final String title = "입장 신청";
        final String body = "프로젝트에 입장 신청이 왔습니다.";

        return new NotificationMessage(title, body);
    }

}
