package com.flab.quicktogether.timeplan.domain.plan;

import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlanJpaRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findByIdAndMemberId(Long planId, Long memberId);

    boolean existsByPlanNameAndTimeBlock(String planName, TimeBlock timeBlock);

    @Query("select distinct pl from Plan pl " +
            "where pl.memberId in (select p.member from Participant p " +
                                  "where p.project.id = :projectId) " +
            "and pl.timeBlock.startDateTime < :limit " +
            "and pl.timeBlock.endDateTime > current_timestamp")
    List<Plan> findByProjectId(@Param("projectId") Long projectId, @Param("limit") LocalDateTime limit);

}
