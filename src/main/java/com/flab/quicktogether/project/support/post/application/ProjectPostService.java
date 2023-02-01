package com.flab.quicktogether.project.support.post.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.project.support.post.domain.Post;
import com.flab.quicktogether.project.support.post.exception.PostNotFoundException;
import com.flab.quicktogether.project.support.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectPostService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    /**
     * 단건 게시글 조회
     */
    public Post retrievePost(Long projectId, Long memberId, Long postId) {
        checkAuthorized(projectId, memberId);
        Post post = findPost(postId);
        return post;
    }

    /**
     * 프로젝트에 전체 게시글 조회
     */
    public List<Post> retrievePosts(Long projectId, Long memberId) {
        checkAuthorized(projectId, memberId);
        List<Post> posts = findPosts(projectId);
        return posts;
    }

    /**
     * 특정 구성원이 등록한 게시글 조회
     */
    public List<Post> retrievePostsByMember(Long projectId, Long memberId) {
        checkAuthorized(projectId, memberId);
        List<Post> posts = findPostsByMember(projectId, memberId);
        return posts;
    }

    @Transactional
    public Long createPost(Long projectId, Long memberId, String content) {
        Project project = checkAuthorized(projectId, memberId);

        Member member = findMember(memberId);
        Post savedPost = postRepository.save(Post.createPost(project, member, content));
        return savedPost.getId();
    }

    @Transactional
    public void updatePost(Long projectId, Long memberId, String newContent, Long postId) {
        checkAuthorized(projectId, memberId);

        Post post = findPost(postId);
        post.changeContent(newContent);
    }
    private Project checkAuthorized(Long projectId, Long memberId) {
        Project project = findProject(projectId);
        project.getParticipants().checkParticipant(memberId);
        return project;
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    private List<Post> findPostsByMember(Long projectId, Long memberId) {
        findProject(projectId);
        findMember(memberId);
        return postRepository.findByProjectIdAndMemberId(projectId, memberId);
    }

    private List<Post> findPosts(Long projectId) {
        findProject(projectId);
        return postRepository.findByProjectId(projectId);
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }


}
