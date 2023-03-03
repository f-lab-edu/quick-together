//package com.flab.quicktogether.meeting.presentation;
//
//import com.flab.quicktogether.common.auth.Login;
//import com.flab.quicktogether.meeting.application.MeetingCatalogService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class MeetingCatalogController {
//
//    private final MeetingCatalogService meetingCatalogService;
//
//    @RequestMapping(path = "/meetings", method = RequestMethod.GET)
//    public ResponseEntity<List<MeetingCatalogDto>> showMeetings(@Login Long memberId, @RequestParam MeetingSearchCondition meetingSearchCondition) {
//        List<MeetingCatalogDto> meetings = meetingCatalogService.getMeetings(memberId, meetingSearchCondition);
//        return new ResponseEntity<>(meetings, HttpStatus.OK);
//    }
//}
