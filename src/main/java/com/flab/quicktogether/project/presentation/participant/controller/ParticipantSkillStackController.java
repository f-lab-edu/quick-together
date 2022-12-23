package com.flab.quicktogether.project.presentation.participant.controller;


import com.flab.quicktogether.project.application.ParticipantService;
import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.presentation.participant.dto.request.EditParticipantSkillStackDto;
import com.flab.quicktogether.project.presentation.participant.dto.response.ParticipantSkillStackResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ParticipantSkillStackController {

    private final ParticipantService participantService;

    /**
     * 구성원 스킬스택 조회
     */
    @GetMapping("/projects/{projectId}/members/{memberId}/skillstacks")
    public ParticipantSkillStackResponseDto retrieveParticipantSkillStack(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId){
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);
        return new ParticipantSkillStackResponseDto(projectId, memberId, findParticipant);
    }

    /**
     * 구성원 스킬스택 추가
     */
    @PostMapping("/projects/{projectId}/members/{memberId}/skillstacks")
    public ParticipantSkillStackResponseDto addPosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                                        @RequestBody @Valid EditParticipantSkillStackDto editParticipantSkillStackDto){

        participantService.addSkillStack(projectId, memberId, editParticipantSkillStackDto);
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        return new ParticipantSkillStackResponseDto(projectId, memberId, findParticipant);
    }

    /**
     * 구성원 스킬스택 삭제
     */
    @DeleteMapping("/projects/{projectId}/members/{memberId}/skillstacks")
    public ParticipantSkillStackResponseDto removePosition(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId,
                                                  @RequestBody @Valid EditParticipantSkillStackDto editParticipantSkillStackDto){

        participantService.removeSkillStack(projectId, memberId, editParticipantSkillStackDto);
        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);

        return new ParticipantSkillStackResponseDto(projectId, memberId, findParticipant);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
