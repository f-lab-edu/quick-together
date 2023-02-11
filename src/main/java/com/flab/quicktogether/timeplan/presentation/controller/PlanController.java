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

    @RequestMapping(path = "/events", method = RequestMethod.POST)
    public ResponseEntity addPlan(@Login Long loginMemberId,
                                  @Valid @RequestBody PlanCreateRequestDto event, BindingResult bindingResult) {
        planService.registerPlan(loginMemberId, event);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/events/{eventId}", method = RequestMethod.PUT)
    public ResponseEntity editEvent(@Login Long loginMemberId,
                                    @PathVariable Long eventId,
                                    @Valid @RequestBody PlanUpdateRequestDto planUpdateRequestDto,
                                    BindingResult bindingResult) {
        planService.modifyPlan(loginMemberId, eventId, planUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/events/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity removeEvent(@Login Long loginMemberId, @PathVariable Long eventId) {
        planService.removePlan(loginMemberId, eventId);
        return ResponseEntity.ok().build();
    }
}
