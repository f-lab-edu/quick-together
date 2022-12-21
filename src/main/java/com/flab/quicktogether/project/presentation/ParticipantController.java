package com.flab.quicktogether.project.presentation;


import com.flab.quicktogether.project.application.ParticipantService;
import com.flab.quicktogether.project.domain.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    /**
     * 해당 프로젝트에 참여하고 있는 멤버들
     */
    @GetMapping("/projects/{id}/members")
    public Result<ParticipantResponseDto> retrieveAllParticipants(@PathVariable("id") Long id){

        List<Participant> findParticipants = participantService.retrieveAllParticipants(id);

        List<ParticipantResponseDto> collect = findParticipants.stream()
                .map(p -> new ParticipantResponseDto(p))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * 해당 프로젝트에 참여하고 있는 멤버
     */
    @GetMapping("/projects/{projectId}/members/{id}")
    public ParticipantResponseDto retrieveParticipant(@PathVariable("projectId") Long projectId, @PathVariable("memberId)") Long memberId){

        Participant findParticipant = participantService.retrieveParticipant(projectId, memberId);
        ParticipantResponseDto participantResponseDto = new ParticipantResponseDto(findParticipant);

        return participantResponseDto;
    }

    /**
     * 해당 프로젝트에 멤버 추가
     */
    @PostMapping("/projects/{projectId}/members/{id}")
    public HttpStatus joinProject(@PathVariable("projectId") Long projectId, @PathVariable("memberId)") Long memberId){

        participantService.joinProject(projectId,memberId);

        return HttpStatus.CREATED;
    }

    /**
     * 해당 프로젝트에 멤버 삭제
     */
    @DeleteMapping("/projects/{projectId}/members/{id}")
    public HttpStatus leaveProject(@PathVariable("projectId") Long projectId, @PathVariable("memberId)") Long memberId){

        participantService.leaveProject(projectId,memberId);

        return HttpStatus.OK;
    }



    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
