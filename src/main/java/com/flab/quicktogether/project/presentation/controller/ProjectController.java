package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectRequestDto;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.presentation.dto.request.CreateProjectRequest;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectRequest;
import com.flab.quicktogether.project.presentation.dto.response.ProjectResponse;
import jakarta.validation.Valid;
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
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 전체 조회
     */
    @GetMapping("/projects")
    public Result projects() {
        List<Project> findProjects = projectService.retrieveAllProjects();

        List<ProjectResponse> collect = findProjects.stream()
                .map(p -> new ProjectResponse(p))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * 프로젝트 단일 조회
     */
    @GetMapping("/projects/{id}")
    public ProjectResponse project(@PathVariable("id") Long id) {
        Project findProject = projectService.retrieveProject(id);
        ProjectResponse projectResponse = new ProjectResponse(findProject);
        return projectResponse;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    /**
     * 프로젝트 등록
     */
    @PostMapping("/projects")
    public ResponseEntity registerProject(@RequestBody @Valid CreateProjectRequest createProjectRequest) {

        CreateProjectRequestDto createProjectRequestDto = createProjectRequest.toServiceDto();
        Long projectId = projectService.createProject(createProjectRequestDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(projectId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * 프로젝트 수정
     */
    @PutMapping("/projects/{id}")
    public HttpStatus editProject(@PathVariable("id") Long id, @RequestBody @Valid EditProjectRequest editProjectRequest) {

        EditProjectRequestDto editProjectRequestDto = editProjectRequest.toServiceDto();
        projectService.editProject(id, editProjectRequestDto);
        Project findProject = projectService.retrieveProject(id);

        return HttpStatus.OK;
    }

    /**
     * 프로젝트 삭제
     */
    @DeleteMapping("/projects/{id}")
    public HttpStatus removeProject(@PathVariable("id") Long id) {

        projectService.deleteProject(id);
        Project findProject = projectService.retrieveProject(id);

        return HttpStatus.OK;
    }
}
