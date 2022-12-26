package com.flab.quicktogether.participant.presentation.controller;


import com.flab.quicktogether.participant.application.ParticipantService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.presentation.dto.request.EditParticipantPositionRequest;
import com.flab.quicktogether.participant.presentation.dto.response.ParticipantPositionResponse;
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
public class ParticipantPositionController {

    private final ParticipantService participantService;

    /**
     * 구성원 포지션 조회
     */
    @GetMapping("/projects/{projectId}/members/{memberId}/positions")
    public ParticipantPositionResponse retrieveParticipantPosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId) {
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);
        return new ParticipantPositionResponse(projectId, memberId, findParticipant);
    }

    /**
     * 구성원 포지션 추가
     */
    @PostMapping("/projects/{projectId}/members/{memberId}/positions")
    public ResponseEntity<ParticipantPositionResponse> addPosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                                                   @RequestBody @Valid EditParticipantPositionRequest editParticipantPositionRequest) {

        participantService.addPosition(projectId, memberId, editParticipantPositionRequest.toServiceDto());
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(new ParticipantPositionResponse(projectId, memberId, findParticipant));
    }

    /**
     * 구성원 포지션 삭제
     */
    @DeleteMapping("/projects/{projectId}/members/{memberId}/positions")
    public ParticipantPositionResponse removePosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                                      @RequestBody @Valid EditParticipantPositionRequest editParticipantPositionRequest) {

        participantService.removePosition(projectId, memberId, editParticipantPositionRequest.toServiceDto());
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        return new ParticipantPositionResponse(projectId, memberId, findParticipant);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
