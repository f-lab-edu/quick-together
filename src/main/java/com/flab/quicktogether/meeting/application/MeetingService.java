package com.flab.quicktogether.meeting.application;

import com.flab.quicktogether.meeting.domain.MeetingRepository;
import com.flab.quicktogether.timeplan.application.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MeetingService {


    private final MeetingRepository meetingRepository;

    private final ScheduleService scheduleService;

    public void register(Long memberId) {

    }

    public void requestRegister() {
    }

    public void modify() {

    }

    public void requestModify() {
    }

    public void delete() {

    }

    public void requestDelete() {

    }
}
