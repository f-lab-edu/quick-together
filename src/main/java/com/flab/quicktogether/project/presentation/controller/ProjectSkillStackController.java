package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.presentation.dto.EditProjectSkillStackDto;
import com.flab.quicktogether.project.presentation.dto.ProjectDto;
import com.flab.quicktogether.project.presentation.dto.ProjectSkillStackResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectSkillStackController {

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
    public ProjectSkillStackResponseDto addProjectSkillStacks(@PathVariable("id") Long id,@RequestBody @Valid EditProjectSkillStackDto editProjectSkillStackDto) {

        projectService.addSkillStack(id, editProjectSkillStackDto);
        Project findProject = projectService.retrieveProject(id);

        return new ProjectSkillStackResponseDto(id, findProject);
    }

    /**
     * 프로젝트 스킬 스택 삭제
     */
    @DeleteMapping("/projects/{id}/skillstacks")
    public ProjectSkillStackResponseDto removeProjectSkillStacks(@PathVariable("id") Long id,@RequestBody @Valid EditProjectSkillStackDto editProjectSkillStackDto) {
        projectService.removeSkillStack(id, editProjectSkillStackDto);
        Project findProject = projectService.retrieveProject(id);

        return new ProjectSkillStackResponseDto(id, findProject);
    }
}
