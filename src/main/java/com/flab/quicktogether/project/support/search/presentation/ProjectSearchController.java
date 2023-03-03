package com.flab.quicktogether.project.support.search.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.project.support.search.application.ProjectSearchService;
import com.flab.quicktogether.project.support.search.presentation.dto.ProjectDetailResponse;
import com.flab.quicktogether.project.support.search.presentation.dto.ProjectMainSimpleResponse;
import com.flab.quicktogether.project.support.search.presentation.dto.ProjectSimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectSearchController {

    private final ProjectSearchService projectSearchService;

    /**
     * 프로젝트 전체 조회
     * 권한: any
     */
    @GetMapping("/projects")
    public Result projects() {
        List<ProjectMainSimpleResponse> projectMainSimpleResponses = projectSearchService.retrieveAllProjects();
        return new Result(projectMainSimpleResponses);
    }

    /**
     * 프로젝트 단일 간단 조회(프로젝트 정보)
     * 권한: any
     */
    @GetMapping("/projects/{projectId}")
    public ProjectSimpleResponse projectSimple(@PathVariable("projectId") Long projectId) {
        ProjectSimpleResponse projectSimpleResponse = projectSearchService.retrieveSimpleProject(projectId);
        return projectSimpleResponse;
    }

    /**
     * 프로젝트 단일 상세 조회(프로젝트 정보, 게시글, 구성원)
     * 권한: 로그인 and 프로젝트 구성원
     */
    @GetMapping("/projects/{projectId}/detail")
    public ProjectDetailResponse projectDetail(@PathVariable("projectId") Long projectId, @Login Long memberId) {
        ProjectDetailResponse projectDetailResponse = projectSearchService.retrieveDetailProject(projectId, memberId);
        return projectDetailResponse;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

}
