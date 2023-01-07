package com.flab.quicktogether.participant.infrastructure;

import com.flab.quicktogether.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Override
    Participant save(Participant participant);

    @Override
    Optional<Participant> findById(Long id);

    @Override
    void delete(Participant participant);

    /**
     * 특정 프로젝트에 특정 구성원 정보
     */
    Optional<Participant> findByProjectIdAndMemberId(Long projectId, Long memberId);

    /**
     * 특정 회원이 참여하고 있는 프로젝트들과 정보
     */
    List<Participant> findByMemberId(Long memberId);

    /**
     * 특정 프로젝트에 참여하고 있는 구성원들
     */
    List<Participant> findByProjectId(Long projectId);


}
