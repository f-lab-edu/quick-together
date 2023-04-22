package com.flab.quicktogether.project.support.search.infrastructure;

import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.support.search.presentation.dto.ProjectSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectSearchRepository extends JpaRepository<Project, Long> {

    /**
     * 프로젝트 상세 조회
     * 프로젝트에 구성원과 멤버를 한 번에 가져온다.
     */
    @Query("select distinct p from Project p " +
            "LEFT JOIN fetch p.skillStacks " +
            "LEFT JOIN fetch p.recruitmentPositions " +
            "JOIN fetch p.participants.participants.member " +
            "LEFT JOIN fetch p.participants.participants.skillStacks " +
            "LEFT JOIN fetch p.participants.participants.positions " +
            "where p.Id = :projectId")
    Optional<Project> findByProjectIdWithDetail(@Param("projectId") Long projectId);

    /**
     * 프로젝트 전체 조회
     * 프로젝트들에 좋아요 개수를 세팅한다.
     */
    @Query("select new com.flab.quicktogether.project.support.search.presentation.dto.ProjectSimpleDto(p,count(l)) from Project p " +
            "left outer join Likes l on l.project = p " +
            "group by p"
    )
    List<ProjectSimpleDto> findByProjectsWithLikes();

    /**
     * 프로젝트 전체 조회 페이징 처리
     * 프로젝트들에 좋아요 개수를 세팅한다.
     */
    @Query("select new com.flab.quicktogether.project.support.search.presentation.dto.ProjectSimpleDto(p,count(l)) from Project p " +
            "left outer join Likes l on l.project = p " +
            "group by p"
    )
    Page<ProjectSimpleDto> findByPagingProjectsWithLikes(Pageable pageable);

    /**
     * 프로젝트 단건 조회
     * 프로젝트에 좋아요 개수를 세팅한다.
     */
    @Query("select new com.flab.quicktogether.project.support.search.presentation.dto.ProjectSimpleDto(p,count(l)) from Project p " +
            "left outer join Likes l on l.project = p " +
            "LEFT JOIN fetch p.skillStacks " +
            "LEFT JOIN fetch p.recruitmentPositions " +
            "where p.Id = :projectId " +
            "group by p"
    )
    Optional<ProjectSimpleDto> findByProjectIdWithLikes(@Param("projectId") Long projectId);

}
