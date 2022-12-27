package com.flab.quicktogether.project.presentation.controller;

import com.flab.quicktogether.project.application.ProjectLikeService;
import com.flab.quicktogether.project.application.ProjectService;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectSkillStackRequest;
import com.flab.quicktogether.project.presentation.dto.response.ProjectLikeResponse;
import com.flab.quicktogether.project.presentation.dto.response.ProjectSkillStackResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProjectLikeController {

    private final ProjectLikeService projectLikeService;

    /**
     * 프로젝트 좋아요 조회
     */
    @GetMapping("/projects/{projectId}/likes")
    public ProjectLikeResponse retrieveProjectLikes(@PathVariable("projectId") Long projectId) {
        Long total = projectLikeService.totalLikes(projectId);
        return new ProjectLikeResponse(projectId, total);
    }

    /**
     * 프로젝트 좋아요 추가
     */
    @PostMapping("/projects/{projectId}/members/{memberId}/likes")
    public ResponseEntity addProjectLikes(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId) {

        projectLikeService.addProjectLike(projectId,memberId);
        Long total = projectLikeService.totalLikes(projectId);

        URI location = URI.create(String.format("/projects/%d/likes", projectId));
        return ResponseEntity.created(location).body(new ProjectLikeResponse(projectId, total));
    }

    /**
     * 프로젝트 좋아요 삭제
     */
    @DeleteMapping("/projects/{projectId}/members/{memberId}/likes")
    public ResponseEntity cancelProjectLikes(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId) {

        projectLikeService.cancelProjectLike(projectId,memberId);
        Long total = projectLikeService.totalLikes(projectId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(new ProjectLikeResponse(projectId, total));
    }
}
