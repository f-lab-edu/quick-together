package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Participant;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParticipantRepository {
    private final EntityManager em;


    public void save(Participant participant) {
        em.persist(participant);
    }

    public Participant findOne(Long id) {
        return em.find(Participant.class, id);
    }

    public void delete(Participant participant) {
        em.remove(participant);
    }

    /**
     * 특정 회원의 특정 프로젝트 정보
     */
    public Participant findByMemberIdAndProjectId(Long memberId, Long projectId) {
        return em.createQuery("select p from Participant p " + "where p.member.id = :memberId and p.project.id = :projectId", Participant.class)
                .setParameter("memberId", memberId)
                .setParameter("projectId", projectId)
                .getSingleResult();
    }


    /**
     * 특정 회원이 참여하고 있는 프로젝트들과 정보
     */
    public List<Participant> findParticipantsByMemberId(Long memberId){
        return em.createQuery("select p from Participant p join p.member m where m.id = :memberId", Participant.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }


    /**
     * 특정 프로젝트에 참여하고 있는 구성원들
     */
    public List<Participant> findParticipantsByProjectId(Long projectId){
        return em.createQuery("select p from Participant p join p.project t where t.id = :projectId", Participant.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }
}
