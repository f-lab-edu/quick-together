package com.flab.quicktogether.participant.infrastructure;

import com.flab.quicktogether.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    /**
     * 특정 프로젝트에 특정 구성원 정보
     */
    @Query("select distinct p from Participant p " +
            "LEFT JOIN fetch p.skillStacks " +
            "LEFT JOIN fetch p.positions " +
            "where p.project.Id = :projectId and p.member.Id = :memberId" )
    Optional<Participant> findByProjectIdAndMemberId(@Param("projectId") Long projectId, @Param("memberId") Long memberId);

    /**
     * 특정 회원이 참여하고 있는 프로젝트들과 정보
     */
    List<Participant> findByMemberId(Long memberId);

    /**
     * 특정 프로젝트에 참여하고 있는 구성원들
     */
    List<Participant> findByProjectId(Long projectId);


    /**
     * 프로젝트 어드민
     */
    @Query("select p from Participant p " +
            "where p.Id = :projectId and p.participantRole = ROLE_ADMIN")
    Optional<Participant> findAdminByProjectId(@Param("projectId") Long projectId);

}
