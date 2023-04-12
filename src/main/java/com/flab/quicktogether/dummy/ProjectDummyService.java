package com.flab.quicktogether.dummy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;


import com.flab.quicktogether.common.SkillStack;
import com.flab.quicktogether.member.domain.Member;
import com.flab.quicktogether.member.infrastructure.MemberRepository;
import com.flab.quicktogether.project.domain.MeetingMethod;
import com.flab.quicktogether.project.domain.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectDummyService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public void insertDummyProjects(int count) {
        Member member = new Member("seungjae","1111");
        entityManager.persist(member);
        entityManager.flush();


        IntStream.range(0, count)
                .forEach(i -> {
                    Set<SkillStack> skills = new HashSet<>();
                    skills.add(SkillStack.JAVA);
                    skills.add(SkillStack.ANGULAR_JS);
                    Project project = Project.builder()
                            .projectName(i+ " 첫번째 프로젝트")
                            .founder(member)
                            .startDateTime(LocalDateTime.now())
                            .periodDateTime(LocalDateTime.now())
                            .meetingMethod(MeetingMethod.SLACK)
                            .projectSummary("간단할 설명~")
                            .projectDescription("긴설명~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                            .skillStacks(skills)
                            .build();
                    //project.getParticipants().registerFounder(project,member);
                    entityManager.persist(project);
                });
    }
}