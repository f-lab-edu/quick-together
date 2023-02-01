package com.flab.quicktogether.project.support.post.presentation;

import com.flab.quicktogether.common.auth.Login;
import com.flab.quicktogether.project.support.post.application.ProjectPostService;
import com.flab.quicktogether.project.support.post.domain.Post;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProjectPostController {

    private final ProjectPostService projectPostService;

    /**
     * 프로젝트 전체 게시글 조회
     */
    @GetMapping("/projects/{projectId}/posts")
    public Result<PostResponse> retrievePosts(@PathVariable("projectId") Long projectId,
                                              @Login Long memberId) {

        List<Post> posts = projectPostService.retrievePosts(projectId, memberId);

        List<PostResponse> collect = posts.stream()
                .map(post -> PostResponse.toDto(post))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    /**
     * 단건 게시글 조회
     */
    @GetMapping("/projects/{projectId}/posts/{postId}")
    public ResponseEntity retrievePost(@PathVariable("projectId") Long projectId,
                                       @PathVariable("postId") Long postId,
                                       @Login Long memberId) {

        Post post = projectPostService.retrievePost(projectId, memberId, postId);
        return ResponseEntity.ok(PostResponse.toDto(post));
    }

    @PostMapping("/projects/{projectId}/posts")
    public ResponseEntity createPost(@PathVariable("projectId") Long projectId,
                                     @Login Long memberId,
                                     @RequestBody @Valid PostRequest postRequest) {

        Long postId = projectPostService.createPost(projectId, memberId, postRequest.getContent());
        return ResponseEntity.ok(new PostIdResponse(postId));
    }

    @PutMapping("/projects/{projectId}/posts/{postId}")
    public ResponseEntity updatePost(@PathVariable("projectId") Long projectId,
                                     @PathVariable("postId") Long postId,
                                     @Login Long memberId,
                                     @RequestBody @Valid PostRequest postRequest) {

        projectPostService.updatePost(projectId, memberId, postRequest.getContent(), postId);
        return ResponseEntity.ok().build();
    }

}
