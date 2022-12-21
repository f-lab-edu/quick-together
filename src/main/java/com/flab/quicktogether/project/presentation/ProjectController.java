package com.flab.quicktogether.project.presentation;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.domain.Project;
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
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 조회
     */
    @GetMapping("/projects")
    public Result projects() {
        List<Project> findProjects = projectService.findProjects();

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
        Project findProject = projectService.findProject(id);
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
    public HttpStatus registerProject(@RequestBody @Validated CreateProjectDto createProjectDto) {

        Long projectId = projectService.createProject(createProjectDto);
        Project findProject = projectService.findProject(projectId);

        return HttpStatus.CREATED;
    }

    /**
     * 프로젝트 수정
     */
    @PutMapping("/projects/{id}")
    public HttpStatus editProject(@PathVariable("id") Long id, @RequestBody @Validated EditProjectDto editProjectDto) {

        projectService.editProject(id, editProjectDto);
        Project findProject = projectService.findProject(id);

        return HttpStatus.OK;
    }
}
