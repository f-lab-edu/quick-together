package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    public Optional<Project> findByFounder(Member Founder);
}
