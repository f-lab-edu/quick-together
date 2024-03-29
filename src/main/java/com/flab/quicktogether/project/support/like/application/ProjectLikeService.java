package com.flab.quicktogether.project.support.like.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.project.support.like.domain.Likes;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.support.like.exception.DuplicateProjectLikeException;
import com.flab.quicktogether.project.support.like.exception.ProjectNoLikeException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.support.like.infrastructure.ProjectLikeRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectLikeService {

    private final ProjectLikeRepository projectLikeRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void addProjectLike(Long projectId, Long memberId) {
        projectLikeRepository.findByProjectIdAndMemberId(projectId, memberId)
                .ifPresentOrElse(
                        findLikes -> {
                            throw new DuplicateProjectLikeException();
                        },
                        () -> {
                            Likes addLikes = new Likes(findProject(projectId), findMember(memberId));
                            projectLikeRepository.save(addLikes);
                        }
                );
    }

    @Transactional
    public void cancelProjectLike(Long projectId, Long memberId) {
        projectLikeRepository.findByProjectIdAndMemberId(projectId, memberId)
                .ifPresentOrElse(
                        findLikes -> projectLikeRepository.delete(findLikes),
                        () -> {
                            throw new ProjectNoLikeException();
                        }
                );
    }

    public Long totalLikes(Long projectId) {
        findProject(projectId);
        Long total = projectLikeRepository.countByProjectId(projectId);
        return total;
    }

    public boolean isLiked(Long projectId, Long memberId){
        return projectLikeRepository.findByProjectIdAndMemberId(projectId, memberId).isPresent();
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
