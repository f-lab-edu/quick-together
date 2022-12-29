package com.flab.quicktogether.participant.presentation.controller;


import com.flab.quicktogether.participant.application.ParticipantService;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.participant.presentation.dto.response.ParticipantResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    /**
     * 해당 프로젝트에 참여하고 있는 전체 멤버
     */
    @GetMapping("/projects/{id}/members")
    public Result<ParticipantResponse> retrieveAllParticipants(@PathVariable("id") Long id){

        List<Participant> findParticipants = participantService.retrieveAllParticipants(id);

        List<ParticipantResponse> collect = findParticipants.stream()
                .map(p -> new ParticipantResponse(p))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * 해당 프로젝트에 참여하고 있는 멤버
     */
    @GetMapping("/projects/{projectId}/members/{memberId}")
    public ParticipantResponse retrieveParticipant(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId){

        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);
        ParticipantResponse participantResponse = new ParticipantResponse(findParticipant);

        return participantResponse;
    }

    /**
     * 해당 프로젝트에 멤버 추가
     */
    @PostMapping("/projects/{projectId}/members/{memberId}")
    public ResponseEntity joinProject(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId){

        participantService.joinProject(projectId,memberId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * 해당 프로젝트에 멤버 삭제
     */
    @DeleteMapping("/projects/{projectId}/members/{memberId}")
    public HttpStatus leaveProject(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId){

        participantService.leaveProject(projectId,memberId);

        return HttpStatus.OK;
    }



    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
