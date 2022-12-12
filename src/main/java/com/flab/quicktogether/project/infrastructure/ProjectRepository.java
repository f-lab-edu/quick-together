package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Project;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public void save(Project project) {
        em.persist(project);
    }

    public Project findOne(Long id) {
        return em.find(Project.class, id);
    }

    public void deleteProjectById(Project project) {
        em.remove(project);
    }

    public List<Project> findAll(){
        return em.createQuery("select p from Project p", Project.class)
                .getResultList();
    }

}
