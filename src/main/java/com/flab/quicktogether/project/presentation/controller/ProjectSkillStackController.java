package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectSkillStackRequest;
import com.flab.quicktogether.project.presentation.dto.response.ProjectSkillStackResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProjectSkillStackController {

    private final ProjectService projectService;

    /**
     * 프로젝트 스킬 스택 조회
     */
    @GetMapping("/projects/{id}/skillstacks")
    public ProjectSkillStackResponse retrieveProjectSkillStacks(@PathVariable("id") Long id) {
        Project findProject = projectService.retrieveProject(id);
        return new ProjectSkillStackResponse(id, findProject);
    }

    /**
     * 프로젝트 스킬 스택 추가
     */
    @PostMapping("/projects/{id}/skillstacks")
    public ResponseEntity addProjectSkillStacks(@PathVariable("id") Long id,
                                                @RequestBody @Valid EditProjectSkillStackRequest editProjectSkillStackRequest) {

        projectService.addSkillStack(id, editProjectSkillStackRequest.toServiceDto());
        Project findProject = projectService.retrieveProject(id);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(new ProjectSkillStackResponse(id, findProject));
    }

    /**
     * 프로젝트 스킬 스택 삭제
     */
    @DeleteMapping("/projects/{id}/skillstacks")
    public ResponseEntity<ProjectSkillStackResponse> removeProjectSkillStacks(@PathVariable("id") Long id,
                                                                              @RequestBody @Valid EditProjectSkillStackRequest editProjectSkillStackRequest) {

        projectService.removeSkillStack(id, editProjectSkillStackRequest.toServiceDto());
        Project findProject = projectService.retrieveProject(id);

        return ResponseEntity.ok(new ProjectSkillStackResponse(id, findProject));
    }
}
