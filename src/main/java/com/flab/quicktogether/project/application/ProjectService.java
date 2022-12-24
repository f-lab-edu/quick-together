package com.flab.quicktogether.project.application;

import com.flab.quicktogether.globalsetting.domain.Position;
import com.flab.quicktogether.globalsetting.domain.SkillStack;
import com.flab.quicktogether.member.exception.MemberNotFoundException;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.domain.MemberRepository;
import com.flab.quicktogether.participant.domain.Participant;
import com.flab.quicktogether.project.domain.*;
import com.flab.quicktogether.project.exception.*;
import com.flab.quicktogether.participant.infrastructure.ParticipantRepository;
import com.flab.quicktogether.project.infrastructure.ProjectRepository;
import com.flab.quicktogether.project.presentation.dto.request.CreateProjectRequest;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectRequest;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectRecruitmentPositionsRequest;
import com.flab.quicktogether.project.presentation.dto.request.EditProjectSkillStackRequest;
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


    public Project retrieveProject(Long projectId) {
        return findProject(projectId);
    }


    public List<Project> retrieveAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Long createProject(CreateProjectRequest createProjectRequest) {

        Member member = findMember(createProjectRequest.getMemberId());

        Project project = Project.builder()
                .projectName(createProjectRequest.getProjectName())
                .startDateTime(createProjectRequest.getStartDateTime())
                .periodDateTime(createProjectRequest.getPeriodDateTime())
                .meetingMethod(createProjectRequest.getMeetingMethod())
                .projectSummary(createProjectRequest.getProjectSummary())
                .projectDescription(createProjectRequest.getProjectDescription())
                .build();
        projectRepository.save(project);

        Participant participant = project.registerFounder(member, project);
        participantRepository.save(participant);

        return project.getId();
    }

    private Member findMember(Long memberId) {
        Optional<Member> member = memberRepository.findOne(memberId);
        if (!member.isPresent()) {
            //throw new MemberNotFoundException(String.format("MemberId[%s] not found", memberId));
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
    public void editProject(Long projectId, EditProjectRequest editProjectForm) {

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
            throw new ProjectNotFoundException(PROJECT_NOT_FOUND);
        }
        return project.get();
    }

    /**
     * 프로젝트 스킬스택 추가
     */
    @Transactional
    public void addSkillStack(Long projectId, EditProjectSkillStackRequest editProjectSkillStackRequest){
        Project findProject = findProject(projectId);
        validateDuplicateSkillStack(findProject, editProjectSkillStackRequest.getSkillStack());

        findProject.addSkillStack(editProjectSkillStackRequest.getSkillStack());
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
    public void removeSkillStack(Long projectId, EditProjectSkillStackRequest editProjectSkillStackRequest) {
        Project findProject = findProject(projectId);
        findProject.removeSkillStack(editProjectSkillStackRequest.getSkillStack());
    }

    /**
     * 프로젝트 모집 포지션 추가
     */
    @Transactional
    public void addRecruitmentPosition(Long projectId, EditProjectRecruitmentPositionsRequest editProjectRecruitmentPositionsRequest) {
        Project findProject = findProject(projectId);

        validateDuplicateRecruitmentPosition(findProject, editProjectRecruitmentPositionsRequest.getRecruitmentPosition());

        findProject.addRecruitmentPosition(editProjectRecruitmentPositionsRequest.getRecruitmentPosition());
    }

    private void validateDuplicateRecruitmentPosition(Project project, Position newRecruitmentPosition) {
        List<Position> positions = project.getRecruitmentPositions();
        positions.stream()
                .filter(skillStack -> skillStack.equals(positions))
                .forEach(skillStack -> {
                    throw new DuplicateProjectPositionException(DUPLICATE_PROJECT_POSITION);
                });
    }

    /**
     * 프로젝트 모집 포지션 삭제
     */
    @Transactional
    public void removeRecruitmentPosition(Long projectId, EditProjectRecruitmentPositionsRequest editProjectRecruitmentPositionsRequest) {
        Project findProject = findProject(projectId);
        findProject.removeRecruitmentPosition(editProjectRecruitmentPositionsRequest.getRecruitmentPosition());
    }
}
