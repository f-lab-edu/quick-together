package com.flab.quicktogether.project.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Role {

    private Role availableRole;
    private SkillStack availableSkills;

}


enum SkillStack {
    JAVA, KOTLIN, SPRING, JPA,
    JAVA_SCRIPT, NODE_JS, EXPREESS_JS, NEST_JS, REACT_JS, VUE_JS, SVELTE_JS, ANGULAR_JS,
    PYTHON, DJANGO, FLASK,

}
enum RoleType {
    PROJECT_MANAGER, DESIGNER, FRONTEND, BACKEND, DATABASE_ADMINISTRATION
}