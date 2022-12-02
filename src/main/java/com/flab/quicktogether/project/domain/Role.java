package com.flab.quicktogether.project.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Role {

    private RoleType availableRoleType;
    private SkillStack availableSkills;

}



