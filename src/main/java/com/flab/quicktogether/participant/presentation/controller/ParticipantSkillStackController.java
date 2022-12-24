package com.flab.quicktogether.participant.presentation.controller;


import com.flab.quicktogether.participant.application.ParticipantService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.presentation.dto.request.EditParticipantSkillStackRequest;
import com.flab.quicktogether.participant.presentation.dto.response.ParticipantSkillStackResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ParticipantSkillStackController {

    private final ParticipantService participantService;

    /**
     * 구성원 스킬스택 조회
     */
    @GetMapping("/projects/{projectId}/members/{memberId}/skillstacks")
    public ParticipantSkillStackResponse retrieveParticipantSkillStack(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId){
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);
        return new ParticipantSkillStackResponse(projectId, memberId, findParticipant);
    }

    /**
     * 구성원 스킬스택 추가
     */
    @PostMapping("/projects/{projectId}/members/{memberId}/skillstacks")
    public ResponseEntity<ParticipantSkillStackResponse> addPosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                                                     @RequestBody @Valid EditParticipantSkillStackRequest editParticipantSkillStackRequest){

        participantService.addSkillStack(projectId, memberId, editParticipantSkillStackRequest);
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(new ParticipantSkillStackResponse(projectId, memberId, findParticipant));
    }

    /**
     * 구성원 스킬스택 삭제
     */
    @DeleteMapping("/projects/{projectId}/members/{memberId}/skillstacks")
    public ParticipantSkillStackResponse removePosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                                        @RequestBody @Valid EditParticipantSkillStackRequest editParticipantSkillStackRequest){

        participantService.removeSkillStack(projectId, memberId, editParticipantSkillStackRequest);
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        return new ParticipantSkillStackResponse(projectId, memberId, findParticipant);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
