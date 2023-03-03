package com.flab.quicktogether.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitSkillStack is a Querydsl query type for RecruitSkillStack
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitSkillStack extends EntityPathBase<RecruitSkillStack> {

    private static final long serialVersionUID = 755697249L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitSkillStack recruitSkillStack = new QRecruitSkillStack("recruitSkillStack");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProject project;

    public final EnumPath<com.flab.quicktogether.common.SkillStack> skillStack = createEnum("skillStack", com.flab.quicktogether.common.SkillStack.class);

    public QRecruitSkillStack(String variable) {
        this(RecruitSkillStack.class, forVariable(variable), INITS);
    }

    public QRecruitSkillStack(Path<? extends RecruitSkillStack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitSkillStack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitSkillStack(PathMetadata metadata, PathInits inits) {
        this(RecruitSkillStack.class, metadata, inits);
    }

    public QRecruitSkillStack(Class<? extends RecruitSkillStack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}

