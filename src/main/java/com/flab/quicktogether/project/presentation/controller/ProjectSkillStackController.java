package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectSkillStackDto;
import com.flab.quicktogether.project.presentation.dto.response.ProjectSkillStackResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProjectSkillStackController {

    private final String LOCATION_URL = "/projects/%d/skillstacks";
    private final ProjectService projectService;

    /**
     * 프로젝트 스킬 스택 조회
     */
    @GetMapping("/projects/{id}/skillstacks")
    public ProjectSkillStackResponseDto retrieveProjectSkillStacks(@PathVariable("id") Long id) {
        Project findProject = projectService.retrieveProject(id);
        return new ProjectSkillStackResponseDto(id, findProject);
    }

    /**
     * 프로젝트 스킬 스택 추가
     */
    @PostMapping("/projects/{id}/skillstacks")
    public ResponseEntity<ProjectSkillStackResponseDto> addProjectSkillStacks(@PathVariable("id") Long id,
                                                                              @RequestBody @Valid EditProjectSkillStackDto editProjectSkillStackDto) {

        projectService.addSkillStack(id, editProjectSkillStackDto);
        Project findProject = projectService.retrieveProject(id);

        URI location = URI.create(String.format(LOCATION_URL, id));

        return ResponseEntity.created(location).body(new ProjectSkillStackResponseDto(id, findProject));
    }

    /**
     * 프로젝트 스킬 스택 삭제
     */
    @DeleteMapping("/projects/{id}/skillstacks")
    public ResponseEntity<ProjectSkillStackResponseDto> removeProjectSkillStacks(@PathVariable("id") Long id,
                                                                                 @RequestBody @Valid EditProjectSkillStackDto editProjectSkillStackDto) {
        projectService.removeSkillStack(id, editProjectSkillStackDto);
        Project findProject = projectService.retrieveProject(id);

        return ResponseEntity.ok(new ProjectSkillStackResponseDto(id, findProject));
    }
}
