package com.flab.quicktogether.timeplan.presentation.controller;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.timeplan.application.PlannerSettingService;
import com.flab.quicktogether.timeplan.presentation.dto.PlannerSettingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/planner/setting")
@RequiredArgsConstructor
public class PlannerSettingController{

    private final PlannerSettingService plannerSettingService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> registerPlannerSetting(@Login Long loginMemberId,
                                                       @RequestBody PlannerSettingDto plannerSettingDto){
        Long response = plannerSettingService.regist(loginMemberId, plannerSettingDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PlannerSettingDto> showPlannerSetting(@Login Long loginMemberId){
        PlannerSettingDto dto = plannerSettingService.show(loginMemberId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
