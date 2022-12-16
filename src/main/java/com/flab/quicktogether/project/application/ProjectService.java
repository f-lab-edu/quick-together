package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.ParticipantRole;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.presentation.EditProjectFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final ParticipantRepository participantRepository;

    @Transactional
    public void createProject(Long memberId, Project project){

        Member findMember = memberRepository.findOne(memberId);

         projectRepository.save(project);

         Participant participant = new Participant(findMember, project, ParticipantRole.ROLE_ADMIN);
         participantRepository.save(participant);

    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project findProject = projectRepository.findOne(projectId);
        projectRepository.deleteProjectById(findProject);
    }

    @Transactional
    public void editProject(Long projectId, EditProjectFormDto editProjectForm) {
        Project findProject = projectRepository.findOne(projectId);
        findProject.editProject(editProjectForm);
    }


    public Project findProject(Long projectId) {
        Project findProject = projectRepository.findOne(projectId);
        return findProject;
    }

    public List<Project> findProjects() {
        return projectRepository.findAll();
    }


}
