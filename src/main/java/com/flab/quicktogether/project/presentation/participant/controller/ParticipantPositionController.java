package com.flab.quicktogether.project.presentation.participant.controller;


import com.flab.quicktogether.project.application.ParticipantService;
import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.presentation.participant.dto.request.EditParticipantPositionDto;
import com.flab.quicktogether.project.presentation.participant.dto.response.ParticipantPositionResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ParticipantPositionController {

    private final ParticipantService participantService;

    /**
     * 구성원 포지션 조회
     */
    @GetMapping("/projects/{projectId}/members/{memberId}/positions")
    public ParticipantPositionResponseDto retrieveParticipantPosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId){
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);
        return new ParticipantPositionResponseDto(projectId, memberId, findParticipant);
    }

    /**
     * 구성원 포지션 추가
     */
    @PostMapping("/projects/{projectId}/members/{memberId}/positions")
    public ParticipantPositionResponseDto addPosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                                      @RequestBody @Valid EditParticipantPositionDto editParticipantPositionDto){

        participantService.addPosition(projectId, memberId, editParticipantPositionDto);
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        return new ParticipantPositionResponseDto(projectId, memberId, findParticipant);
    }

    /**
     * 구성원 포지션 삭제
     */
    @DeleteMapping("/projects/{projectId}/members/{memberId}/positions")
    public ParticipantPositionResponseDto removePosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                                  @RequestBody @Valid EditParticipantPositionDto editParticipantPositionDto){

        participantService.removePosition(projectId, memberId, editParticipantPositionDto);
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        return new ParticipantPositionResponseDto(projectId, memberId, findParticipant);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
