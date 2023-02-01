package com.flab.quicktogether.project.support.post.infrastructure;

import com.flab.quicktogether.project.support.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByProjectId(Long projectId);
    List<Post> findByProjectIdAndMemberId(Long projectId, Long memberId);
}
