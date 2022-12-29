package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    Project save(Project project);

    @Override
    Optional<Project> findById(Long id);

    @Override
    List<Project> findAll();

    @Override
    void delete(Project entity);
}
