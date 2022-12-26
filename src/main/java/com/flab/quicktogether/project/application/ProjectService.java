package com.flab.quicktogether.project.application;

import com.flab.quicktogether.globalsetting.domain.Position;
import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.project.application.dto.CreateProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectRequestDto;
import com.flab.quicktogether.project.application.dto.EditProjectSkillStackRequestDto;
import com.flab.quicktogether.project.application.dto.EditRecruitmentPositionsRequestDto;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.exception.*;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectLikeRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.flab.quicktogether.globalsetting.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;

    private final ParticipantRepository participantRepository;

    private final ProjectLikeRepository projectLikeRepository;


    public Project retrieveProject(Long projectId) {
        Project project = findProject(projectId);
        project.changeProjectLikes(projectLikeRepository.findTotalLikesByProjectId(projectId));
        return project;
    }

    public List<Project> retrieveAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Long createProject(CreateProjectRequestDto createProjectRequestDto) {

        Member member = findMember(createProjectRequestDto.getMemberId());
        Project project = createProjectRequestDto.createProject(member);

        projectRepository.save(project);
        participantRepository.save(project.registerFounder(member, project));

        return project.getId();
    }

    private Member findMember(Long memberId) {
        Optional<Member> member = memberRepository.findOne(memberId);
        if (!member.isPresent()) {
            throw new MemberNotFoundException(MEMBER_NOT_FOUND);
        }
        return member.get();
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
    private Project findProject(Long projectId) {
        Optional<Project> project = projectRepository.findOne(projectId);
        if (!project.isPresent()) {
            throw new ProjectNotFoundException(PROJECT_NOT_FOUND);
        }
        return project.get();
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
                    throw new DuplicateProjectSkillStackException(DUPLICATE_PROJECT_SKILLSTACK);
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
                .forEach(skillStack -> {
                    throw new DuplicateProjectPositionException(DUPLICATE_PROJECT_POSITION);
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
}
