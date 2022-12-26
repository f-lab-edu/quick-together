package com.flab.quicktogether.timeplan.presentation;

import com.flab.quicktogether.common.Login;
import com.flab.quicktogether.timeplan.application.TimePlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/timeplan")
@RequiredArgsConstructor
public class TimePlanController {

    private final TimePlanService timePlanService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<TimePlanGetRequestDto> getTimePlan(@Login Long loginMemberId) {
        TimePlanGetRequestDto timePlanGetRequestDto = timePlanService.getTimePlan(loginMemberId);
        return ResponseEntity.ok(timePlanGetRequestDto);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> postTimePlan(@Login Long loginMemberId,
                                                   @Valid @RequestBody TimePlanCreateRequestDto timePlanCreateRequestDto, BindingResult bindingResult) {
        timePlanService.createTimePlan(loginMemberId, timePlanCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(path = "/routine", method = RequestMethod.PUT)
    public ResponseEntity updateAbleRoutine(@Login Long loginMemberId,
                                            @Valid @RequestBody List<AbleRoutineUpdateRequestDto> ableRoutinesRequestDtos) {
        timePlanService.updateAbleRoutine(loginMemberId, ableRoutinesRequestDtos);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/events", method = RequestMethod.POST)
    public ResponseEntity addEvent(@Login Long loginMemberId,
                                   @Valid @RequestBody EventCreateRequestDto event, BindingResult bindingResult) {
        timePlanService.createPlannedEvent(loginMemberId, event);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/events/{eventId}", method = RequestMethod.PUT)
    public ResponseEntity editEvent(@Login Long loginMemberId,
                                   @PathVariable Long eventId,
                                   @Valid @RequestBody EventUpdateRequestDto eventUpdateRequestDto, BindingResult bindingResult) {
        timePlanService.updateEvent(loginMemberId, eventUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/events/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity removeEvent(@Login Long loginMemberId, @PathVariable Long eventId) {
        timePlanService.deletePlannedEvent(eventId);
        return ResponseEntity.ok().build();
    }


}
