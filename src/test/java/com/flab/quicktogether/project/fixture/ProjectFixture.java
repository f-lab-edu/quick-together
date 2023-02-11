package com.flab.quicktogether.project.fixture;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.timeplan.domain.setting.MinuteUnit;

import java.time.LocalDateTime;

public class ProjectFixture {
    public static final Member FOUNDER = new Member("founder");

    public static final Project NORMAL_FIXTURE1 = Project.builder()
            .projectName("첫번째 프로젝트")
            .founder(FOUNDER)
            .startDateTime(LocalDateTime.now())
            .periodDateTime(LocalDateTime.now())
            .meetingMethod(MeetingMethod.SLACK)
            .projectSummary("간단할 설명~")
            .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
            .build();
}
