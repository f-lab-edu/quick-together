package com.flab.quicktogether.timeplan.presentation.controller;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.timeplan.application.ScheduleService;
import com.flab.quicktogether.timeplan.presentation.dto.RoughlyPlanDto;
import com.flab.quicktogether.timeplan.presentation.dto.SuggestedTimeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    @RequestMapping(path = "/schedules/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<SuggestedTimeDto> informAvailableTime(@Login Long memberId,
                                                                @PathVariable("projectId") Long projectId,
                                                                @ModelAttribute RoughlyPlanDto roughlyPlanDto) {
        SuggestedTimeDto suggestedTimeDto = scheduleService
                .suggestAvailableTime(memberId, projectId, roughlyPlanDto);

        return ResponseEntity.ok(suggestedTimeDto);
    }
}
