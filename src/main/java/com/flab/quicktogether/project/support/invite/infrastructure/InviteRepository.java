package com.flab.quicktogether.project.support.invite.infrastructure;

import com.flab.quicktogether.project.support.invite.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Optional<Invite> findByProjectIdAndInvitedMemberId(Long projectId, Long invitedMemberId);

    /**
     * 프로젝트에 초대되어 WAIT 상태인 것을 반환
     */
    @Query("select i from Invite i " +
            "join fetch i.project p " +
            "join fetch i.invitedMember m " +
            "where p.Id = :projectId and m.Id = :invitedMember and i.inviteStatus = WAIT")
    Optional<Invite> findByProjectIdAndInvitedMemberIdWithWait(@Param("projectId") Long projectId, @Param("invitedMember") Long invitedMember);
}
