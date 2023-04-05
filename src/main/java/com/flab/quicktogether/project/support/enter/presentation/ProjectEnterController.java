package com.flab.quicktogether.project.support.enter.presentation;


import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.project.support.enter.application.ProjectEnterService;
import com.flab.quicktogether.project.support.enter.domain.Enter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
     * 해당 멤버가 입장신청한 리스트
     */
    @GetMapping("/enters")
    public Result enteredAllMember(@Login Long memberId) {
        List<ProjectEnterResponse> enterMembers = projectEnterService.retrieveAllEnterMember(memberId);
        return new Result(enterMembers);
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
     * 해당 프로젝트 입장 신청한 멤버들 조회
     */
    @GetMapping("/projects/{projectId}/members/enters")
    public Result retrieveAllEnteredMember(@PathVariable("projectId") Long projectId,
                                                   @Login Long memberId) {
        List<ProjectEnterResponse> enterMembers = projectEnterService.retrieveAllEnterMemberByProjectId(projectId);
        return new Result(enterMembers);
    }

    /**
     * 프로젝트 입장 신청 수락
     */
    @PostMapping("/projects/{projectId}/members/enters")
    public ResponseEntity acceptEnter(@PathVariable("projectId") Long projectId,
                                      @Login Long adminMemberId,
                                      @RequestBody @Valid EnterMemberRequest enterMemberRequest) {

        projectEnterService.acceptEnter(projectId, adminMemberId, enterMemberRequest.getEnterMemberId());
        return ResponseEntity.ok().build();
    }

    /**
     * 프로젝트 입장 신청 거절
     */
    @DeleteMapping("/projects/{projectId}/members/enters")
    public ResponseEntity rejectEnter(@PathVariable("projectId") Long projectId,
                                      @Login Long adminMemberId,
                                      @RequestBody @Valid EnterMemberRequest enterMemberRequest) {

        projectEnterService.rejectEnter(projectId, adminMemberId, enterMemberRequest.getEnterMemberId());
        return ResponseEntity.ok().build();
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
