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
    public Result project() {
        List<Project> findProjects = projectService.findProjects();

        List<ProjectDto> collect = findProjects.stream()
                .map(p -> new ProjectDto(p))
                .collect(Collectors.toList());

        return new Result(collect);
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
    public HttpStatus addProject(@RequestBody @Validated CreateProjectFormDto createProjectFormDto) {

        Project project = Project.builder()
                .projectName(createProjectFormDto.getProjectName())
                .startDateTime(createProjectFormDto.getStartDateTime())
                .periodDateTime(createProjectFormDto.getPeriodDateTime())
                .meetingMethod(createProjectFormDto.getMeetingMethod())
                .projectSummary(createProjectFormDto.getProjectSummary())
                .description(createProjectFormDto.getProjectDescription())
                .build();

        projectService.createProject(1L, project);

        return HttpStatus.CREATED;
    }

    /**
     * 프로젝트 수정
     */
    @PutMapping("/projects/{id}")
    public HttpStatus editProject(@PathVariable("id") Long id, @RequestBody @Validated EditProjectFormDto editProjectFormDto) {

        projectService.editProject(id, editProjectFormDto);
        Project findProject = projectService.findProject(id);

        return HttpStatus.OK;
    }
}
