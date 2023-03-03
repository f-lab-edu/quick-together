package com.flab.quicktogether.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitPosition is a Querydsl query type for RecruitPosition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitPosition extends EntityPathBase<RecruitPosition> {

    private static final long serialVersionUID = 505589587L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitPosition recruitPosition = new QRecruitPosition("recruitPosition");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.flab.quicktogether.common.Position> position = createEnum("position", com.flab.quicktogether.common.Position.class);

    public final QProject project;

    public QRecruitPosition(String variable) {
        this(RecruitPosition.class, forVariable(variable), INITS);
    }

    public QRecruitPosition(Path<? extends RecruitPosition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitPosition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitPosition(PathMetadata metadata, PathInits inits) {
        this(RecruitPosition.class, metadata, inits);
    }

    public QRecruitPosition(Class<? extends RecruitPosition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}

