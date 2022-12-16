package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.presentation.EditProjectFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;

    private final ParticipantRepository participantRepository;

    @Transactional
    public void createProject(Long memberId, Project project) {

        Member findMember = memberRepository.findOne(memberId);

        projectRepository.save(project);

        Participant participant = project.registerFounder(findMember, project);
        participantRepository.save(participant);

    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project findProject = projectRepository.findOne(projectId);
        findProject.changeProjectStatus(ProjectStatus.DELETED);
    }

    @Transactional
    public void editProject(Long projectId, EditProjectFormDto editProjectForm) {

        Project findProject = projectRepository.findOne(projectId);

        findProject.changeProjectName(editProjectForm.getProjectName());
        findProject.changeStartDateTime(editProjectForm.getStartDateTime());
        findProject.changePeriodDate(editProjectForm.getPeriodDateTime());
        findProject.changeMeetingMethod(editProjectForm.getMeetingMethod());
        findProject.changeProjectDescriptionInfo(new ProjectDescriptionInfo(editProjectForm.getProjectSummary(), editProjectForm.getProjectDescription()));
        findProject.changeProjectStatus(editProjectForm.getProjectStatus());
    }


    public Project findProject(Long projectId) {
        Project findProject = projectRepository.findOne(projectId);
        return findProject;
    }

    public List<Project> findProjects() {
        return projectRepository.findAll();
    }


}
