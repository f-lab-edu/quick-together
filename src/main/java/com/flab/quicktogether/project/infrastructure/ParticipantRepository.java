package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Participant;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
