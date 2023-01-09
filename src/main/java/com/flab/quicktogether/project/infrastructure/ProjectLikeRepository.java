package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectLikeRepository extends JpaRepository<Likes, Long> {

    @Override
    Likes save(Likes likes);

    @Override
    Optional<Likes> findById(Long id);

    Long countByProjectId(Long projectId);

    @Override
    void delete(Likes likes);

    Optional<Likes> findByProjectIdAndMemberId(Long projectId, Long memberId);


}
