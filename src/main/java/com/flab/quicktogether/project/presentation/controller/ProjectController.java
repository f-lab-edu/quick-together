package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectRequestDto;
import com.flab.quicktogether.project.presentation.dto.request.CreateProjectRequest;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

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

        return HttpStatus.OK;
    }

    /**
     * 프로젝트 삭제
     */
    @DeleteMapping("/projects/{id}")
    public HttpStatus removeProject(@PathVariable("id") Long id) {

        projectService.deleteProject(id);

        return HttpStatus.OK;
    }
}
