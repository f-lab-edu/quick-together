package com.flab.quicktogether.participant.presentation.controller;


import com.flab.quicktogether.participant.application.ParticipantService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.presentation.dto.request.ChangeParticipantRoleRequest;
import com.flab.quicktogether.participant.presentation.dto.response.ParticipantRoleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ParticipantRoleController {

    private final ParticipantService participantService;

    /**
     * 구성원 권한 조회
     */
    @GetMapping("/projects/{projectId}/members/{memberId}/roles")
    public ParticipantRoleResponse retrieveParticipantRole(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId){
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);
        return new ParticipantRoleResponse(projectId, memberId, findParticipant);
    }

    /**
     * 구성원 권한 변경
     */
    @PutMapping("/projects/{projectId}/members/{memberId}/roles")
    public ParticipantRoleResponse editRole(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                            @RequestBody @Valid ChangeParticipantRoleRequest changeParticipantRoleRequest){

        participantService.changeRole(projectId, memberId, changeParticipantRoleRequest.toServiceDto());
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        return new ParticipantRoleResponse(projectId, memberId, findParticipant);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
