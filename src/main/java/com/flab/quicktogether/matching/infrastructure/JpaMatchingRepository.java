package com.flab.quicktogether.matching.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMatchingRepository {

    @PersistenceContext
    EntityManager em;





}
