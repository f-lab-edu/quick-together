package com.flab.quicktogether.project.application;

import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.exception.DuplicateParticipantSkillStackException;
import com.flab.quicktogether.project.exception.MemberNotFoundException;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import com.flab.quicktogether.project.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.presentation.dto.CreateProjectDto;
import com.flab.quicktogether.project.presentation.dto.EditProjectDto;
import com.flab.quicktogether.project.presentation.dto.EditProjectSkillStackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;

    private final ParticipantRepository participantRepository;


    public Project retrieveProject(Long projectId) {
        return findProject(projectId);
    }


    public List<Project> retrieveAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Long createProject(CreateProjectDto createProjectDto) {

        Member member = findMember(createProjectDto.getMemberId());

        Project project = Project.builder()
                .projectName(createProjectDto.getProjectName())
                .startDateTime(createProjectDto.getStartDateTime())
                .periodDateTime(createProjectDto.getPeriodDateTime())
                .meetingMethod(createProjectDto.getMeetingMethod())
                .projectSummary(createProjectDto.getProjectSummary())
                .projectDescription(createProjectDto.getProjectDescription())
                .build();
        projectRepository.save(project);

        Participant participant = project.registerFounder(member, project);
        participantRepository.save(participant);

        return project.getId();
    }

    private Member findMember(Long memberId) {
        Optional<Member> member = memberRepository.findOne(memberId);
        if (!member.isPresent()) {
            throw new MemberNotFoundException(String.format("MemberId[%s] not found", memberId));
        }
        return member.get();
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project findProject = findProject(projectId);
        findProject.changeProjectStatus(ProjectStatus.DELETED);
    }

    @Transactional
    public void editProject(Long projectId, EditProjectDto editProjectForm) {

        Project findProject = findProject(projectId);

        findProject.changeProjectName(editProjectForm.getProjectName());
        findProject.changeStartDateTime(editProjectForm.getStartDateTime());
        findProject.changePeriodDate(editProjectForm.getPeriodDateTime());
        findProject.changeMeetingMethod(editProjectForm.getMeetingMethod());
        findProject.changeProjectDescriptionInfo(new ProjectDescriptionInfo(editProjectForm.getProjectSummary(), editProjectForm.getProjectDescription()));
        findProject.changeProjectStatus(editProjectForm.getProjectStatus());
    }
    private Project findProject(Long projectId) {
        Optional<Project> project = projectRepository.findOne(projectId);
        if (!project.isPresent()) {
            throw new ProjectNotFoundException(String.format("ProjectId[%s] not found", projectId));
        }
        return project.get();
    }

    /**
     * 프로젝트 스킬스택 추가
     */
    @Transactional
    public void addSkillStack(Long projectId, EditProjectSkillStackDto editProjectSkillStackDto){
        Project findProject = findProject(projectId);

        validateDuplicateSkillStack(findProject, editProjectSkillStackDto.getSkillStack());

        findProject.addSkillStack(editProjectSkillStackDto.getSkillStack());
    }

    private void validateDuplicateSkillStack(Project project, SkillStack newSkillStack) {
        List<SkillStack> skillStacks = project.getSkillStacks();
        skillStacks.stream()
                .filter(skillStack -> skillStack.equals(newSkillStack))
                .forEach(skillStack -> {
                    throw new DuplicateParticipantSkillStackException("이미 존재하는 스킬입니다.");
                });
    }

    /**
     * 프로젝트 스킬스택 삭제
     */
    @Transactional
    public void removeSkillStack(Long projectId, EditProjectSkillStackDto editProjectSkillStackDto) {
        Project findProject = findProject(projectId);
        findProject.removeSkillStack(editProjectSkillStackDto.getSkillStack());

    }

}
