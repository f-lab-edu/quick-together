package com.flab.quicktogether.project.support.search.application;

import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.support.search.presentation.dto.ProjectDetailResponse;
import com.flab.quicktogether.project.support.search.infrastructure.ProjectSearchRepository;
import com.flab.quicktogether.project.support.search.presentation.dto.ProjectMainSimpleResponse;
import com.flab.quicktogether.project.support.search.presentation.dto.ProjectSimpleDto;
import com.flab.quicktogether.project.support.search.presentation.dto.ProjectSimpleResponse;
import com.flab.quicktogether.project.support.like.infrastructure.ProjectLikeRepository;
import com.flab.quicktogether.project.support.post.application.ProjectPostService;
import com.flab.quicktogether.project.support.post.domain.Post;
import com.flab.quicktogether.project.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProjectSearchService {

    private final ProjectSearchRepository projectSearchRepository;

    private final ProjectLikeRepository projectLikeRepository;

    private final ProjectPostService projectPostService;


    /**
     * 프로젝트 간단 조회 (프로젝트 정보)
     */
    @Transactional
    public ProjectSimpleResponse retrieveSimpleProject(Long projectId) {
        ProjectSimpleDto projectSimpleDto = findProjectWithLikes(projectId);

        Project project = projectSimpleDto.getProject();
        project.plusViews();

        ProjectSimpleResponse projectSimpleResponse = new ProjectSimpleResponse(projectSimpleDto.getProject(), projectSimpleDto.getLikes());

        return projectSimpleResponse;
    }

    /**
     * 프로젝트 상세 조회 (프로젝트 정보, 구성원, 게시글)
     */
    public ProjectDetailResponse retrieveDetailProject(Long projectId, Long memberId) {
        List<Post> posts = projectPostService.retrievePosts(projectId, memberId);

        Project project = findProjectDetail(projectId);
        Long likes = projectLikeRepository.countByProjectId(projectId);

        ProjectDetailResponse projectDetailResponse = new ProjectDetailResponse(project, likes, posts);

        return projectDetailResponse;
    }

    public List<ProjectMainSimpleResponse> retrieveAllProjects() {
        List<ProjectSimpleDto> projects = projectSearchRepository.findByProjectsWithLikes();

        List<ProjectMainSimpleResponse> collect = projects.stream()
                .map(findProject -> new ProjectMainSimpleResponse(findProject.getProject(), findProject.getLikes()))
                .collect(Collectors.toList());

        log.info("projects size = {}", projects.size());
        return collect;
    }

    private ProjectSimpleDto findProjectWithLikes(Long projectId) {
        return projectSearchRepository.findByProjectIdWithLikes(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }


    private Project findProjectDetail(Long projectId) {
        return projectSearchRepository.findByProjectIdWithDetail(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }

}
