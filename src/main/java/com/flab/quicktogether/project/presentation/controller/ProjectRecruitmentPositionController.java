package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectRecruitmentPositionsDto;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectSkillStackDto;
import com.flab.quicktogether.project.presentation.dto.response.ProjectRecruitmentPositionsResponseDto;
import com.flab.quicktogether.project.presentation.dto.response.ProjectSkillStackResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectRecruitmentPositionController {

    private final ProjectService projectService;

    /**
     * 프로젝트 모집 포지션 조회
     */
    @GetMapping("/projects/{id}/recruitment/positions")
    public ProjectRecruitmentPositionsResponseDto retrieveProjectRecruitmentPositions(@PathVariable("id") Long id) {
        Project findProject = projectService.retrieveProject(id);
        return new ProjectRecruitmentPositionsResponseDto(id, findProject);
    }

    /**
     * 프로젝트 모집 포지션 추가
     */
    @PostMapping("/projects/{id}/recruitment/positions")
    public ProjectRecruitmentPositionsResponseDto addProjectRecruitmentPositions(@PathVariable("id") Long id,
                                                                                 @RequestBody @Valid EditProjectRecruitmentPositionsDto editProjectRecruitmentPositionsDto) {
        projectService.addRecruitmentPosition(id, editProjectRecruitmentPositionsDto);
        Project findProject = projectService.retrieveProject(id);

        return new ProjectRecruitmentPositionsResponseDto(id, findProject);
    }

    /**
     * 프로젝트 모집 포지션 삭제
     */
    @DeleteMapping("/projects/{id}/recruitment/positions")
    public ProjectRecruitmentPositionsResponseDto removeProjectRecruitmentPositions(@PathVariable("id") Long id,
                                                                                    @RequestBody @Valid EditProjectRecruitmentPositionsDto editProjectRecruitmentPositionsDto) {
        projectService.removeRecruitmentPosition(id, editProjectRecruitmentPositionsDto);
        Project findProject = projectService.retrieveProject(id);

        return new ProjectRecruitmentPositionsResponseDto(id, findProject);
    }
}
