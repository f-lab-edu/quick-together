package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.presentation.CreateProjectDto;
import com.flab.quicktogether.project.presentation.EditProjectDto;
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


    public Project findProject(Long projectId) {
        Project findProject = projectRepository.findOne(projectId);
        return findProject;
    }

    public List<Project> findProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Long createProject(CreateProjectDto createProjectDto) {

        Member findMember = memberRepository.findOne(createProjectDto.getMemberId());

        Project project = Project.builder()
                .projectName(createProjectDto.getProjectName())
                .startDateTime(createProjectDto.getStartDateTime())
                .periodDateTime(createProjectDto.getPeriodDateTime())
                .meetingMethod(createProjectDto.getMeetingMethod())
                .projectSummary(createProjectDto.getProjectSummary())
                .description(createProjectDto.getProjectDescription())
                .build();
        projectRepository.save(project);

        Participant participant = project.registerFounder(findMember, project);
        participantRepository.save(participant);

        return project.getId();
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project findProject = projectRepository.findOne(projectId);
        findProject.changeProjectStatus(ProjectStatus.DELETED);
    }

    @Transactional
    public void editProject(Long projectId, EditProjectDto editProjectForm) {

        Project findProject = projectRepository.findOne(projectId);

        findProject.changeProjectName(editProjectForm.getProjectName());
        findProject.changeStartDateTime(editProjectForm.getStartDateTime());
        findProject.changePeriodDate(editProjectForm.getPeriodDateTime());
        findProject.changeMeetingMethod(editProjectForm.getMeetingMethod());
        findProject.changeProjectDescriptionInfo(new ProjectDescriptionInfo(editProjectForm.getProjectSummary(), editProjectForm.getProjectDescription()));
        findProject.changeProjectStatus(editProjectForm.getProjectStatus());
    }



}
