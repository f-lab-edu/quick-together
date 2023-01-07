package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Optional<Invite> findByProjectIdAndInvitedMemberId(Long projectId, Long invitedMemberId);
}
