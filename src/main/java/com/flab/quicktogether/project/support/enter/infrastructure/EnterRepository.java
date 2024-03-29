package com.flab.quicktogether.project.support.enter.infrastructure;

import com.flab.quicktogether.project.support.enter.domain.Enter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnterRepository extends JpaRepository<Enter, Long> {
    @Query("select e from Enter e " +
            "join fetch e.project p " +
            "join fetch e.enterMember m " +
            "where p.Id = :projectId and m.Id = :enterMember and e.enterStatus = WAIT")
    Optional<Enter> findByProjectIdAndEnterMemberIdWithWait(@Param("projectId") Long projectId, @Param("enterMember") Long enterMemberId);

    @Query("select e from Enter e " +
            "join fetch e.project p " +
            "join fetch e.enterMember m " +
            "where m.Id = :enterMember and e.enterStatus = WAIT")
    List<Enter> findByEnterMemberIdWithWait(@Param("enterMember") Long enterMemberId);

    @Query("select e from Enter e " +
            "join fetch e.project p " +
            "join fetch e.enterMember m " +
            "where p.Id = :projectId and e.enterStatus = WAIT")
    List<Enter> findByProjectIdWithWait(@Param("projectId") Long projectId);
}
