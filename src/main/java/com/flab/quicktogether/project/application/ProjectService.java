package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Participant;
import com.flab.quicktogether.project.domain.ParticipantRole;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.presentation.CreateProjectFormDto;
import com.flab.quicktogether.project.presentation.EditProjectFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final ParticipantRepository participantRepository;

    public void createProject(Long memberId, CreateProjectFormDto createProjectFormDto){

        Member findMember = memberRepository.findOne(memberId);


        Project project = Project.builder()
                .projectName(createProjectFormDto.getProjectName())
                .startDateTime(createProjectFormDto.getStartDateTime())
                .periodDate(createProjectFormDto.getPeriodDate())
                .meetingMethod(createProjectFormDto.getMeetingMethod())
                .projectSummary(createProjectFormDto.getProjectSummary())
                .description(createProjectFormDto.getDescription())
                .build();

         projectRepository.save(project);

         Participant participant = new Participant(findMember, project, ParticipantRole.ROLE_ADMIN);
         participantRepository.save(participant);

    }

    public void deleteProject(Long projectId) {
        Project deleteProject = findProject(projectId);
        projectRepository.deleteProjectById(deleteProject);
    }

    public Project findProject(Long projectId) {
        Project findProject = projectRepository.findOne(projectId);
        return findProject;
    }

    public List<Project> findProjects() {
        return projectRepository.findAll();
    }

    public Project editProject(EditProjectFormDto editProjectForm, Long projectId) {
        Project findProject = findProject(projectId);
        findProject.editProject(editProjectForm);
        return findProject;
    }
}
