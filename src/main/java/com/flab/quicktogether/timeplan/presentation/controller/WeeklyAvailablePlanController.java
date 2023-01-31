package com.flab.quicktogether.timeplan.presentation.controller;

import com.flab.quicktogether.common.Login;
import com.flab.quicktogether.timeplan.application.WeeklyAvailablePlanService;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanCreateRequestDto;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanGetDto;
import com.flab.quicktogether.timeplan.presentation.dto.AvailablePlanRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/available_plans")
@RequiredArgsConstructor
public class WeeklyAvailablePlanController {

    private final WeeklyAvailablePlanService availableTimeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AvailablePlanGetDto> getTimePlan(@Login Long loginMemberId,
                                                           @RequestParam String timezone) {
        AvailablePlanGetDto timePlanGetRequestDto = availableTimeService.getAvailablePlan(loginMemberId, timezone);
        return ResponseEntity.ok(timePlanGetRequestDto);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> postTimePlan(@Login Long loginMemberId,
                                                   @Valid @RequestBody AvailablePlanCreateRequestDto timePlanCreateRequestDto, BindingResult bindingResult) {
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
