package com.flab.quicktogether.project.support.invite.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.project.support.invite.application.ProjectInviteService;
import com.flab.quicktogether.project.support.invite.domain.Invite;
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
public class ProjectInviteController {

    private final ProjectInviteService projectInviteService;

    /**
     * 프로젝트에 초대된 멤버 조회
     * todo:관리자 권한으로 변경
     */
    @GetMapping("/projects/{projectId}/members/{invitedMemberId}/invites")
    public ResponseEntity inviteMember(@PathVariable("projectId") Long projectId, @PathVariable("invitedMemberId") Long invitedMemberId) {

        Invite invite = projectInviteService.retrieveInvitedMember(projectId, invitedMemberId);
        return ResponseEntity.ok(new ProjectInviteResponse(invite));
    }

    /**
     * 특정 멤버 초대장들
     */
    @GetMapping("/invites")
    public Result inviteMember(@Login Long invitedMemberId) {
        List<ProjectInviteResponse> invites = projectInviteService.retrieveAllInvitation(invitedMemberId);
        return new Result(invites);
    }


    /**
     * 프로젝트 멤버 초대
     */
    @PostMapping("/projects/{projectId}/invites")
    public ResponseEntity inviteMember(@PathVariable("projectId") Long projectId,
                                       @RequestBody @Valid InviteMemberRequest inviteMemberRequest,
                                       @Login Long requestMemberId) {
        projectInviteService.inviteMember(projectId, requestMemberId, inviteMemberRequest.getInvitedMemberId());
        URI location = URI.create(String.format("/projects/%d/members/%d/invites", projectId, inviteMemberRequest.getInvitedMemberId()));
        return ResponseEntity.created(location).build();
    }

    /**
     * 프로젝트 초대 수락
     */
    @PostMapping("/projects/{projectId}/members/invites")
    public ResponseEntity acceptInvite(@PathVariable("projectId") Long projectId,
                                       @Login Long invitedMemberId) {

        projectInviteService.acceptInvite(projectId, invitedMemberId);

        return ResponseEntity.ok().build();
    }

    /**
     * 프로젝트 초대 거절
     */
    @DeleteMapping("/projects/{id}/members/invites")
    public ResponseEntity rejectInvite(@PathVariable("projectId") Long projectId,
                                       @Login Long invitedMemberId) {

        projectInviteService.rejectInvite(projectId, invitedMemberId);

        return ResponseEntity.ok().build();
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
