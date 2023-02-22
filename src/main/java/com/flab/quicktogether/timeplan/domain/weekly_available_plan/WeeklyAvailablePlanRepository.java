package com.flab.quicktogether.timeplan.domain.weekly_available_plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WeeklyAvailablePlanRepository extends JpaRepository<WeeklyAvailablePlan, Long> {

    Optional<WeeklyAvailablePlan> findByMemberId(Long memberId);

    @Query("select distinct war from WeeklyAvailablePlan war " +
            "where war.memberId in (select p.member from Participant p " +
                                  "where p.project.id = :projectId)")
    List<WeeklyAvailablePlan> findByProjectId(@Param("projectId") Long projectId);



}
