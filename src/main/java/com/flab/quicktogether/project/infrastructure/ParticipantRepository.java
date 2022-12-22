package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
     * 특정 프로젝트에 특정 구성원 정보
     */
    public Optional<Participant> findByProjectIdAndMemberId(Long projectId, Long memberId) {
        // 결과 값 없을 시 getSingleResult()에서 NoResultException 발생,, 특정 프로젝트에 중복 참여를 막기 위해 해당 exception catch 후 Optional.ofNullable값 반환시키기위해
        Participant participant = null;
        try {
            participant = em.createQuery("select p from Participant p " + "where p.member.id = :memberId and p.project.id = :projectId", Participant.class)
                    .setParameter("memberId", memberId)
                    .setParameter("projectId", projectId)
                    .getSingleResult();
            return Optional.ofNullable(participant);
        } catch (NoResultException ignore) {
            return Optional.ofNullable(participant);
        }



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
    public List<Participant> findByProjectId(Long projectId){
        return em.createQuery("select p from Participant p join p.project t where t.id = :projectId", Participant.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }
}
