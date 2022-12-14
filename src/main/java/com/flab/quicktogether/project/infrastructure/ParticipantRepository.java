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

    /**
     * 특정 프로젝트에 참여하고 있는 구성원들
     */
    public List<Participant> findParticipantByProjectId(Long projectId){
        return em.createQuery("select p from Participant p join p.project t where t.id = :projectId", Participant.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }
}
