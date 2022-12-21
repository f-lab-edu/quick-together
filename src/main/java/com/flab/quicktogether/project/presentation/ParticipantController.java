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


    @PostMapping("/participants")
    public HttpStatus joinProject(@RequestBody @Validated ParticipantDto participantDto){

        participantService.joinProject(participantDto);

        return HttpStatus.CREATED;
    }

    @DeleteMapping("/participants")
    public HttpStatus leaveProject(@RequestBody @Validated ParticipantDto participantDto){

        participantService.leaveProject(participantDto);

        return HttpStatus.OK;
    }

    @GetMapping("/participants/role")
    public Result role(@RequestBody @Validated ParticipantDto participantDto){

        ///==================================

        return new Result("");
    }

    @PutMapping("/participants/role")
    public HttpStatus changeRole(@RequestBody @Validated ChangeParticipantRoleDto changeParticipantRoleDto){

        participantService.changeRole(changeParticipantRoleDto);

        return HttpStatus.OK;
    }

    @GetMapping("/participants/position")
    public Result position(@RequestBody @Validated ParticipantDto participantDto){

        Participant participant = participantService.findParticipant(participantDto);
        List<ParticipantPositionDto> collect = participant.getPositions().stream()
                .map(p -> new ParticipantPositionDto(p))
                .collect(Collectors.toList());
        return new Result(collect);
    }


    @PostMapping("/participants/position")
    public HttpStatus addPosition(@RequestBody @Validated EditParticipantPositionDto editParticipantPositionDto){
        participantService.addPosition(editParticipantPositionDto);

        return HttpStatus.CREATED;
    }

    @DeleteMapping("/participants/position")
    public HttpStatus removePosition(@RequestBody @Validated EditParticipantPositionDto editParticipantPositionDto){
        participantService.removePosition(editParticipantPositionDto);
        return HttpStatus.OK;
    }

    @GetMapping("/participants/skillstack")
    public Result skillStack(@RequestBody @Validated ParticipantDto participantDto){
        Participant participant = participantService.findParticipant(participantDto);
        List<ParticipantSkillStackDto> collect = participant.getSkillStacks().stream()
                .map(p -> new ParticipantSkillStackDto(p))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/participants/skillstack")
    public HttpStatus addSkillStack(@RequestBody @Validated EditParticipantSkillStackDto editParticipantSkillStackDto) {
        participantService.addSkillStack(editParticipantSkillStackDto);

        return HttpStatus.CREATED;
    }

    @DeleteMapping("/participants/skillstack")
    public HttpStatus removeSkillStack(@RequestBody @Validated EditParticipantSkillStackDto editParticipantSkillStackDto){
        participantService.removeSkillStack(editParticipantSkillStackDto);
        return HttpStatus.OK;
    }



    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
