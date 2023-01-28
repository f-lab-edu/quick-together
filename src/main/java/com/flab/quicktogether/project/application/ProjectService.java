package com.flab.quicktogether.project.application;

import com.flab.quicktogether.common.Position;
import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectSkillStackRequestDto;
import com.flab.quicktogether.project.application.dto.EditRecruitmentPositionsRequestDto;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.exception.*;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
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



    public Project retrieveBasicProject(Long projectId) {
        Project project = findProject(projectId);
        return project;
    }

    public List<Project> retrieveAllBasicProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Long createProject(CreateProjectRequestDto createProjectRequestDto) {

        Member member = findMember(createProjectRequestDto.getMemberId());
        Project project = createProjectRequestDto.createProject(member);

        Project savedProject = projectRepository.save(project);
        project.getParticipants().registerFounder(project,member);

        return savedProject.getId();
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project findProject = findProject(projectId);
        findProject.changeProjectStatus(ProjectStatus.DELETED);
    }

    @Transactional
    public void editProject(Long projectId, EditProjectRequestDto editProjectFormDto) {

        Project findProject = findProject(projectId);

        findProject.changeProjectName(editProjectFormDto.getProjectName());
        findProject.changeStartDateTime(editProjectFormDto.getStartDateTime());
        findProject.changePeriodDate(editProjectFormDto.getPeriodDateTime());
        findProject.changeMeetingMethod(editProjectFormDto.getMeetingMethod());
        findProject.changeProjectDescriptionInfo(new ProjectDescriptionInfo(editProjectFormDto.getProjectSummary(), editProjectFormDto.getProjectDescription()));
        findProject.changeProjectStatus(editProjectFormDto.getProjectStatus());
    }

    /**
     * 프로젝트 스킬스택 추가
     */
    @Transactional
    public void addSkillStack(Long projectId, EditProjectSkillStackRequestDto editProjectSkillStackRequestDto){
        Project findProject = findProject(projectId);
        validateDuplicateSkillStack(findProject, editProjectSkillStackRequestDto.getSkillStack());

        findProject.addSkillStack(editProjectSkillStackRequestDto.getSkillStack());
    }

    private void validateDuplicateSkillStack(Project project, SkillStack newSkillStack) {
        List<SkillStack> skillStacks = project.getSkillStacks();
        skillStacks.stream()
                .filter(skillStack -> skillStack.equals(newSkillStack))
                .forEach(skillStack -> {
                    throw new DuplicateProjectSkillStackException();
                });
    }

    /**
     * 프로젝트 스킬스택 삭제
     */
    @Transactional
    public void removeSkillStack(Long projectId, EditProjectSkillStackRequestDto editProjectSkillStackRequestDto) {
        Project findProject = findProject(projectId);
        findProject.removeSkillStack(editProjectSkillStackRequestDto.getSkillStack());
    }

    /**
     * 프로젝트 모집 포지션 추가
     */
    @Transactional
    public void addRecruitmentPosition(Long projectId, EditRecruitmentPositionsRequestDto editRecruitmentPositionsRequestDto) {
        Project findProject = findProject(projectId);

        validateDuplicateRecruitmentPosition(findProject, editRecruitmentPositionsRequestDto.getRecruitmentPosition());

        findProject.addRecruitmentPosition(editRecruitmentPositionsRequestDto.getRecruitmentPosition());
    }

    private void validateDuplicateRecruitmentPosition(Project project, Position newRecruitmentPosition) {
        List<Position> positions = project.getRecruitmentPositions();
        positions.stream()
                .filter(position -> position.equals(newRecruitmentPosition))
                .forEach(position -> {
                    throw new DuplicateProjectPositionException();
                });

    }

    /**
     * 프로젝트 모집 포지션 삭제
     */
    @Transactional
    public void removeRecruitmentPosition(Long projectId, EditRecruitmentPositionsRequestDto editRecruitmentPositionsRequestDto) {
        Project findProject = findProject(projectId);
        findProject.removeRecruitmentPosition(editRecruitmentPositionsRequestDto.getRecruitmentPosition());
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
