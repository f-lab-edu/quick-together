package com.flab.quicktogether.project.presentation.controller;


import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.project.application.ProjectEnterService;
import com.flab.quicktogether.project.domain.Enter;
import com.flab.quicktogether.project.presentation.dto.request.EnterMemberRequest;
import com.flab.quicktogether.project.presentation.dto.response.ProjectEnterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProjectEnterController {

    private final ProjectEnterService projectEnterService;

    /**
     * 프로젝트에 입장 신청한 멤버 조회
     * todo:관리자 권한으로 변경
     */
    @GetMapping("/projects/{projectId}/members/{enterMemberId}/enters")
    public ResponseEntity retrieveMember(@PathVariable("projectId") Long projectId,
                                      @PathVariable("enterMemberId") Long enterMemberId) {

        Enter enter = projectEnterService.retrieveEnterMember(projectId, enterMemberId);
        return ResponseEntity.ok(new ProjectEnterResponse(enter));
    }


    /**
     * 프로젝트에 입장 신청
     */
    @PostMapping("/projects/{projectId}/enters")
    public ResponseEntity enterMember(@PathVariable("projectId") Long projectId,
                                      @Login Long enterMemberId) {

        projectEnterService.requestEnterProject(projectId, enterMemberId);
        URI location = URI.create(String.format("/projects/%d/members/%d/enters", projectId, enterMemberId));
        return ResponseEntity.created(location).build();
    }

    /**
     * 프로젝트 입장 신청 수락
     */
    @PostMapping("/projects/{projectId}/members/enters")
    public ResponseEntity acceptEnter(@PathVariable("projectId") Long projectId,
                                      @Login Long adminMemberId,
                                      @RequestBody @Valid EnterMemberRequest enterMemberRequest) {

        projectEnterService.acceptEnter(projectId, adminMemberId,enterMemberRequest.getEnterMemberId());
        return ResponseEntity.ok().build();
    }

    /**
     * 프로젝트 입장 신청 거절
     */
    @DeleteMapping("/projects/{projectId}/members/enters")
    public ResponseEntity rejectEnter(@PathVariable("projectId") Long projectId,
                                      @Login Long adminMemberId,
                                      @RequestBody @Valid EnterMemberRequest enterMemberRequest) {

        projectEnterService.rejectEnter(projectId, adminMemberId,enterMemberRequest.getEnterMemberId());
        return ResponseEntity.ok().build();
    }
}
