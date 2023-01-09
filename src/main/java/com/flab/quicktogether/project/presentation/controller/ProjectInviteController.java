package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectInviteService;
import com.flab.quicktogether.project.domain.Invite;
import com.flab.quicktogether.project.presentation.dto.request.InviteMemberRequest;
import com.flab.quicktogether.project.presentation.dto.response.ProjectInviteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProjectInviteController {

    private final ProjectInviteService projectInviteService;

    /**
     * 프로젝트에 초대된 멤버 조회
     */
    @GetMapping("/projects/{projectId}/members/{invitedMemberId}/invites")
    public ResponseEntity inviteMember(@PathVariable("projectId") Long projectId, @PathVariable("invitedMemberId") Long invitedMemberId) {

        Invite invite = projectInviteService.retrieveInvitedMember(projectId, invitedMemberId);
        return ResponseEntity.ok(new ProjectInviteResponse(invite));
    }


    /**
     * 프로젝트 멤버 초대
     */
    @PostMapping("/projects/{projectId}/invites")
    public ResponseEntity inviteMember(@PathVariable("projectId") Long projectId, @RequestBody @Valid InviteMemberRequest inviteMemberRequest) {
        projectInviteService.inviteMember(projectId, inviteMemberRequest.getRequestMemberId(), inviteMemberRequest.getInvitedMemberId());
        URI location = URI.create(String.format("/projects/%d/members/%d/invites", projectId, inviteMemberRequest.getInvitedMemberId()));
        return ResponseEntity.created(location).build();
    }

    /**
     * 프로젝트 초대 수락
     */
    @PostMapping("/projects/{projectId}/members/{invitedMemberId}/invites")
    public ResponseEntity acceptInvite(@PathVariable("projectId") Long projectId, @PathVariable("invitedMemberId") Long invitedMemberId) {

        projectInviteService.acceptInvite(projectId, invitedMemberId);

        return ResponseEntity.ok().build();
    }

    /**
     * 프로젝트 초대 거절
     */
    @DeleteMapping("/projects/{projectId}/members/{invitedMemberId}/invites")
    public ResponseEntity rejectInvite(@PathVariable("projectId") Long projectId, @PathVariable("invitedMemberId") Long invitedMemberId) {

        projectInviteService.rejectInvite(projectId, invitedMemberId);

        return ResponseEntity.ok().build();
    }
}
