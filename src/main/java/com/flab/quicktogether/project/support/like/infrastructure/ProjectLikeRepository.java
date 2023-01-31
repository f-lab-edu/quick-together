package com.flab.quicktogether.project.support.like.infrastructure;

import com.flab.quicktogether.project.support.like.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectLikeRepository extends JpaRepository<Likes, Long> {
    Long countByProjectId(Long projectId);
    Optional<Likes> findByProjectIdAndMemberId(Long projectId, Long memberId);
}
