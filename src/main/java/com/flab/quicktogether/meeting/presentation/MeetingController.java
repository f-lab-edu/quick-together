package com.flab.quicktogether.meeting.presentation;

import com.flab.quicktogether.common.Login;
import com.flab.quicktogether.meeting.application.MeetingSchedularService;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/projects")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingSchedularService meetingSchedularService;

    @RequestMapping(path = "/{projectId}/meetingSchedule", method = RequestMethod.POST)
    public ResponseEntity<List<TimeBlock>> giveAvailableTime(@Login Long loginMemberId,
                                                             @PathVariable Long projectId,
                                                             AvailableTimeRequestDto availableTimeRequestDto) {
        List<TimeBlock> availableTimes = meetingSchedularService.suggestAvailableTime(loginMemberId,projectId, availableTimeRequestDto);

        return new ResponseEntity<>(availableTimes, HttpStatus.OK);
    }

}
