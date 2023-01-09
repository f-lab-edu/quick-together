package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.presentation.dto.request.EditRecruitmentPositionsRequest;
import com.flab.quicktogether.project.presentation.dto.response.ProjectRecruitmentPositionsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProjectRecruitmentPositionController {

    private final ProjectService projectService;

    /**
     * 프로젝트 모집 포지션 조회
     */
    @GetMapping("/projects/{id}/recruitment/positions")
    public ProjectRecruitmentPositionsResponse retrieveProjectRecruitmentPositions(@PathVariable("id") Long id) {
        Project findProject = projectService.retrieveProject(id);
        return new ProjectRecruitmentPositionsResponse(id, findProject);
    }

    /**
     * 프로젝트 모집 포지션 추가
     */
    @PostMapping("/projects/{id}/recruitment/positions")
    public ResponseEntity<ProjectRecruitmentPositionsResponse> addProjectRecruitmentPositions(@PathVariable("id") Long id,
                                                                                              @RequestBody @Valid EditRecruitmentPositionsRequest editRecruitmentPositionsRequest) {

        projectService.addRecruitmentPosition(id, editRecruitmentPositionsRequest.toServiceDto());
        Project findProject = projectService.retrieveProject(id);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(new ProjectRecruitmentPositionsResponse(id, findProject));

    }

    /**
     * 프로젝트 모집 포지션 삭제
     */
    @DeleteMapping("/projects/{id}/recruitment/positions")
    public ResponseEntity<ProjectRecruitmentPositionsResponse> removeProjectRecruitmentPositions(@PathVariable("id") Long id,
                                                                                                 @RequestBody @Valid EditRecruitmentPositionsRequest editRecruitmentPositionsRequest) {

        projectService.removeRecruitmentPosition(id, editRecruitmentPositionsRequest.toServiceDto());
        Project findProject = projectService.retrieveProject(id);

        return ResponseEntity.ok(new ProjectRecruitmentPositionsResponse(id, findProject));
    }
}
