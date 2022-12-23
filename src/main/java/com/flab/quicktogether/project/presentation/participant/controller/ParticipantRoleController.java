package com.flab.quicktogether.project.presentation.participant.controller;


import com.flab.quicktogether.project.application.ParticipantService;
import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.presentation.participant.dto.request.ChangeParticipantRoleDto;
import com.flab.quicktogether.project.presentation.participant.dto.response.ParticipantRoleResponseDto;
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
    public ParticipantRoleResponseDto retrieveParticipantRole(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId){
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);
        return new ParticipantRoleResponseDto(projectId, memberId, findParticipant);
    }

    /**
     * 구성원 권한 변경
     */
    @PutMapping("/projects/{projectId}/members/{memberId}/roles")
    public ParticipantRoleResponseDto editRole(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                               @RequestBody @Valid ChangeParticipantRoleDto changeParticipantRoleDto){

        participantService.changeRole(projectId, memberId, changeParticipantRoleDto);
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        return new ParticipantRoleResponseDto(projectId, memberId, findParticipant);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
