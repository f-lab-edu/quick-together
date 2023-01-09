package com.flab.quicktogether.matching.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
@Table(name="role")
public class Role {

    @Column(name="role_type")
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    @Column(name="skill_stack")
    @Enumerated(value = EnumType.STRING)
    private SkillStack skillStack;

    public Role() {
    }

    public Role(RoleType roleType, SkillStack skillStack) {
        this.roleType = roleType;
        this.skillStack = skillStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return roleType == role.roleType && skillStack == role.skillStack;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleType, skillStack);
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleType=" + roleType +
                ", skillStack=" + skillStack +
                '}';
    }
}
