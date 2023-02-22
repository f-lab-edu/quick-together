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
    List<Plan> findByProjectId(@Param("projectId") Long projectId,
                               @Param("limit") LocalDateTime limit);

    @Query("select distinct pl from Plan pl " +
            "where pl.memberId in (select p.member from Participant p " +
            "where p.project.id = :projectId) " +
            "and not(pl.timeBlock.endDateTime <= :from) "+
            "and not(pl.timeBlock.startDateTime >= :to)")
    List<Plan> findByProjectId(@Param("projectId") Long projectId,
                               @Param("from") LocalDateTime from,
                               @Param("to") LocalDateTime to);

    //plan과 meeting의 연관관계가 없어서 planName과 Meeting의 title, 시간블록을 비교하는 형태로 쿼리작성.
    @Query("select pl from Plan pl " +
            "where pl.planName = :planName " +
            "and pl.timeBlock.startDateTime = :from " +
            "and pl.timeBlock.endDateTime = :to " +
            "and pl.planStatus = 'DELETED'")
    List<Plan> findByPlanNameAndEqualTimePeriod(@Param("planName") String planName,
                                                @Param("from") LocalDateTime from,
                                                @Param("to") LocalDateTime to);
}
