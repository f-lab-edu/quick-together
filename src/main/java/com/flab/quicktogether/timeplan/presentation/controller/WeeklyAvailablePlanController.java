package com.flab.quicktogether.timeplan.presentation.controller;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.timeplan.application.WeeklyAvailablePlanService;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanCreateRequestDto;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanGetDto;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/available-plans")
@RequiredArgsConstructor
public class WeeklyAvailablePlanController {

    private final WeeklyAvailablePlanService availableTimeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AvailablePlanGetDto> getTimePlan(@Login Long loginMemberId,
                                                           @RequestParam("timeZone") String timeZone) {
        AvailablePlanGetDto timePlanGetRequestDto = availableTimeService.getAvailablePlan(loginMemberId, timeZone);
        return ResponseEntity.ok(timePlanGetRequestDto);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> postWeeklyAvailablePlan(@Login Long loginMemberId,
                                                   @Valid @RequestBody AvailablePlanCreateRequestDto timePlanCreateRequestDto) {
        log.info("dto={}", timePlanCreateRequestDto);
        availableTimeService.registerWeeklyAvailableRoutine(loginMemberId, timePlanCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateAbleRoutine(@Login Long loginMemberId,
                                            @Valid @RequestBody List<AvailablePlanRequestDto> ableRoutinesRequestDtos) {
        availableTimeService.updateAvailablePlan(loginMemberId, ableRoutinesRequestDtos);
        return ResponseEntity.ok().build();
    }


}
