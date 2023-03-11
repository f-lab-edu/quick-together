package com.flab.quicktogether.timeplan.presentation.controller;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.timeplan.application.PlanService;
import com.flab.quicktogether.timeplan.presentation.dto.PlanCreateRequestDto;
import com.flab.quicktogether.timeplan.presentation.dto.PlanUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity editplan(@Login Long loginMemberId,
                                    @PathVariable("planId") Long planId,
                                    @Valid @RequestBody PlanUpdateRequestDto planUpdateRequestDto,
                                    BindingResult bindingResult) {
        planService.modifyPlan(loginMemberId, planId, planUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/plans/{planId}", method = RequestMethod.DELETE)
    public ResponseEntity removeplan(@Login Long loginMemberId, @PathVariable("planId") Long planId) {
        planService.removePlan(loginMemberId, planId);
        return ResponseEntity.ok().build();
    }
}
