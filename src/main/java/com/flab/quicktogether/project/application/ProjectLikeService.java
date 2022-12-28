package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.project.domain.Likes;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.exception.DuplicateProjectLikeException;
import com.flab.quicktogether.project.exception.ProjectNoLikeException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.ProjectLikeRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.flab.quicktogether.globalsetting.exception.ErrorCode.*;

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
                            throw new DuplicateProjectLikeException(DUPLICATE_PROJECT_LIKE);
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
                            throw new ProjectNoLikeException(NO_LIKE);
                        }
                );
    }

    public Long totalLikes(Long projectId) {
        findProject(projectId);
        Long total = projectLikeRepository.countByProjectId(projectId);
        return total;
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException(PROJECT_NOT_FOUND));
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }
}
