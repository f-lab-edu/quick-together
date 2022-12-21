package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Project;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    @Autowired
    private final EntityManager em;

    public void save(Project project) {
        em.persist(project);
    }

    public Optional<Project> findOne(Long id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }

    public void delete(Project project) {
        em.remove(project);
    }

    public List<Project> findAll(){
        return em.createQuery("select p from Project p", Project.class)
                .getResultList();
    }

}
