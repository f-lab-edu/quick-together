package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.domain.Project;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

        List<ProjectDto> collect = findProjects.stream()
                .map(p -> new ProjectDto(p))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * 프로젝트 단일 조회
     */
    @GetMapping("/projects/{id}")
    public Result project(@PathVariable("id") Long id) {
        Project findProject = projectService.retrieveProject(id);
        ProjectDto projectDto = new ProjectDto(findProject);
        return new Result(projectDto);
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
    public ResponseEntity registerProject(@RequestBody @Valid CreateProjectDto createProjectDto) {

        Long projectId = projectService.createProject(createProjectDto);

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
    public HttpStatus editProject(@PathVariable("id") Long id, @RequestBody @Valid EditProjectDto editProjectDto) {

        projectService.editProject(id, editProjectDto);
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
