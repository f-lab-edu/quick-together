package com.flab.quicktogether.meeting.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.meeting.application.MeetingService;
import com.flab.quicktogether.meeting.presentation.dto.MeetingRequestDto;
import com.flab.quicktogether.meeting.presentation.dto.MeetingResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/projects")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @RequestMapping(path = "/{projectId}/meetings", method = RequestMethod.POST)
    public ResponseEntity makeMeeting(@Login Long loginMemberId,
                                      @PathVariable("projectId") Long projectId,
                                      @RequestBody MeetingRequestDto meetingRequestDto) {

        Long createdId = meetingService.regist(loginMemberId, projectId, meetingRequestDto);

        //프론트서버가 뷰를 따로 그려준다면 PRG패턴에 API uri를 적용하는것이 적절하지 않아보임.
        //API의 뷰가 아니라 프론트의 뷰를 반환하도록해야하지 않나 생각됨.
        URI locationPath = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdId)
                .toUri();

        return ResponseEntity
                .created(locationPath)
                .build();
    }

    @RequestMapping(path = "/{projectId}/meetings/{meetingId}", method = RequestMethod.GET)
    public ResponseEntity<MeetingResponseDto> showMeeting(@Login Long loginMemberId,
                                                          @PathVariable("meetingId") Long meetingId,
                                                          @RequestParam("timeZone") String timeZone) {
        MeetingResponseDto dto = meetingService.getMeeting(loginMemberId, meetingId, timeZone);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(path = "/{projectId}/meeting-proposals/", method = RequestMethod.POST)
    public ResponseEntity requestToMakeMeeting(@Login Long loginMemberId,
                                               @PathVariable("projectId") Long projectId,
                                               @RequestBody MeetingRequestDto meetingRequestDto) {
        meetingService.requestRegistration(loginMemberId, projectId, meetingRequestDto);

        return responseOk();
    }

    //추후에 이벤트소싱을 통해서 Meeting이 만들어진다면 plan service가 나누어지면서 단순 스테이터스변경의 PUT메서드로 변할여지가 있는데
    //이 경우에도 전체적인 멱등성을 고려해서 POST로 두는것이 맞겠죠?
    @RequestMapping(path = "/{projectId}/meeting-proposals/{meetingId}", method = RequestMethod.POST)
    public ResponseEntity approveForMakingMeeting(@Login Long loginMemberId, @PathVariable("meetingId") Long meetingId) {

        meetingService.accept(loginMemberId, meetingId);

        return responseOk();
    }

    @RequestMapping(path = "/{projectId}/meeting-proposals/{meetingId}", method = RequestMethod.PUT)
    public ResponseEntity rejectForMakingMeeting(@Login Long loginMemberId, @PathVariable("meetingId") Long meetingId) {

        meetingService.deny(loginMemberId, meetingId);
        return responseOk();
    }

    //projectId가 path구조의 일관성을 이유로 필요한데 서비스상에서 projectId가 필요가 없다면 project의 하부구조로 project부분을 쓰지 않고 사용해도 될까요?
    @RequestMapping(path = "/{projectId}/meetings/{meetingId}", method = RequestMethod.PUT)
    public ResponseEntity editMeeting(@Login Long loginMemberId,
                                      @PathVariable("meetingId") Long meetingId,
                                      @RequestBody MeetingRequestDto meetingRequestDto) {

        meetingService.modify(loginMemberId, meetingId, meetingRequestDto);

        return responseOk();
    }

    @RequestMapping(path = "/{projectId}/meetings/{meetingId}/proposals", method = RequestMethod.POST)
    public ResponseEntity requestToEditMeeting(@Login Long loginMemberId,
                                               @PathVariable("meetingId") Long meetingId,
                                               @RequestBody MeetingRequestDto meetingRequestDto) {

        meetingService.requestModification(loginMemberId, meetingId, meetingRequestDto);

        return responseOk();
    }


    @RequestMapping(path = "/{projectId}/meetings/{meetingId}?status=REQUESTED", method = RequestMethod.GET)
    public ResponseEntity<List<MeetingResponseDto>> showMeetingsRequested(@Login Long loginMemberId,
                                                                          @PathVariable("projectId") Long projectId,
                                                                          @RequestParam("timeZone") String timeZone) {

        List<MeetingResponseDto> dtos = meetingService.getMeetingForApproval(loginMemberId, projectId, timeZone);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @RequestMapping(path = "/{projectId}/meetings/{meetingId}/proposals/", method = RequestMethod.PUT)
    public ResponseEntity requestToCancelMeeting(@Login Long loginMemberId, @PathVariable("meetingId") Long meetingId) {

        meetingService.requestCancelation(loginMemberId,meetingId);
        return responseOk();
    }

    //TODO 등업, 강등, 탈퇴, 가입 구현예정


    private ResponseEntity<Object> responseOk() {
        return ResponseEntity.ok().build();
    }
}
