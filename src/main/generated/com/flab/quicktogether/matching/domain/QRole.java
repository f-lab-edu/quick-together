package com.flab.quicktogether.matching.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRole extends BeanPath<Role> {

    private static final long serialVersionUID = 227605488L;

    public static final QRole role = new QRole("role");

    public final EnumPath<RoleType> roleType = createEnum("roleType", RoleType.class);

    public final EnumPath<SkillStack> skillStack = createEnum("skillStack", SkillStack.class);

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata metadata) {
        super(Role.class, metadata);
    }

}

