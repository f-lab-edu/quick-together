package com.flab.quicktogether.timeplan.presentation.controller;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.timeplan.application.PlanService;
import com.flab.quicktogether.timeplan.presentation.dto.PlanCreateRequestDto;
import com.flab.quicktogether.timeplan.presentation.dto.PlanGetResponseDto;
import com.flab.quicktogether.timeplan.presentation.dto.PlanSearchCondition;
import com.flab.quicktogether.timeplan.presentation.dto.PlanUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @RequestMapping(path = "/plans", method = RequestMethod.POST)
    public ResponseEntity addPlan(@Login Long loginMemberId,
                                  @Valid @RequestBody PlanCreateRequestDto plan) {
        planService.registerPlan(loginMemberId, plan);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/plans/{planId}", method = RequestMethod.PUT)
    public ResponseEntity editPlan(@Login Long loginMemberId,
                                    @PathVariable("planId") Long planId,
                                    @Valid @RequestBody PlanUpdateRequestDto planUpdateRequestDto) {
        planService.modifyPlan(loginMemberId, planId, planUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/plans/{planId}", method = RequestMethod.DELETE)
    public ResponseEntity removePlan(@Login Long loginMemberId, @PathVariable("planId") Long planId) {
        planService.removePlan(loginMemberId, planId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/plans", method= RequestMethod.GET)
    public ResponseEntity<List<PlanGetResponseDto>> showPlans(@Login Long loginMemberId,
                                                              @ModelAttribute PlanSearchCondition condition){

        log.info("condition={}", condition);
        List<PlanGetResponseDto> plans = planService.getPlans(loginMemberId, condition);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

}
