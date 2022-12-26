package com.flab.quicktogether.project.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import com.flab.quicktogether.project.presentation.dto.request.CreateProjectRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    EntityManager em;

    @BeforeEach
    @Rollback(value = false)
    void initEach() {

        Member member = new Member("승재");
        em.persist(member);

        Project project = Project.builder()
                .projectName("첫번째 프로젝트")
                .startDateTime(LocalDateTime.now())
                .periodDateTime(LocalDateTime.now())
                .meetingMethod(MeetingMethod.SLACK)
                .projectSummary("간단할 설명~")
                .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                .build();

        em.persist(project);
    }

    @Test
    @DisplayName("프로젝트 조회 컨트롤러 테스트")
    void selectProject() throws Exception {

        //given
        String projectName = "첫번째 프로젝트";
        String meetingMethod= "SLACK";

        //when
        mockMvc.perform(get("/projects")

        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.data[0].projectName").value(projectName))
        .andExpect(jsonPath("$.data[0].meetingMethod").value(meetingMethod))
        .andDo(print());


    }

    @Test
    @DisplayName("프로젝트 생성 테스트")
    void addProject() throws Exception {
        //given
        String projectName = "에프랩프로젝트"; // 프로젝트 이름
        LocalDateTime startDateTime = LocalDateTime.now(); // 시작일
        LocalDateTime periodDate = LocalDateTime.now(); // 모집기간
        MeetingMethod meetingMethod = MeetingMethod.SLACK; // 미팅 방법
        String projectSummary = "간단할 설명~"; // 프로젝트 간단 설명
        String description = "긴설명~~~~~~~~~~~~~~~~~~~~~~~~~"; // 프로젝트 상세설명

        //when

        CreateProjectRequest createProjectRequest = new CreateProjectRequest(1L,projectName,startDateTime,periodDate,meetingMethod,projectSummary,description);
        String body = mapper.writeValueAsString(createProjectRequest);

        //then
        mockMvc.perform(post("/projects")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());


    }
}