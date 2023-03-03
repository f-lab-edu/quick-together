package com.flab.quicktogether.project.support.like.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.project.support.like.application.ProjectLikeService;
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
    @PostMapping("/projects/{projectId}/likes")
    public ResponseEntity addProjectLikes(@PathVariable("projectId") Long projectId, @Login Long memberId) {

        projectLikeService.addProjectLike(projectId,memberId);
        Long total = projectLikeService.totalLikes(projectId);

        URI location = URI.create(String.format("/projects/%d/likes", projectId));
        return ResponseEntity.created(location).body(new ProjectLikeResponse(projectId, total));
    }

    /**
     * 회원의 좋아요 여부
     */
    @GetMapping("/me/{projectId}/likes")
    public ProjectIsLikedResponse retrieveProjectLikes(@PathVariable("projectId") Long projectId, @Login Long memberId) {
        boolean liked = projectLikeService.isLiked(projectId, memberId);
        return new ProjectIsLikedResponse(projectId, liked);
    }

    /**
     * 프로젝트 좋아요 삭제
     */
    @DeleteMapping("/projects/{projectId}/likes")
    public ResponseEntity cancelProjectLikes(@PathVariable("projectId") Long projectId, @Login Long memberId) {

        projectLikeService.cancelProjectLike(projectId,memberId);
        Long total = projectLikeService.totalLikes(projectId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(new ProjectLikeResponse(projectId, total));
    }
}
