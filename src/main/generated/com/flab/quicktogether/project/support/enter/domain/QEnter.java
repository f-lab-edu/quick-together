package com.flab.quicktogether.project.support.enter.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEnter is a Querydsl query type for Enter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEnter extends EntityPathBase<Enter> {

    private static final long serialVersionUID = -571462663L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEnter enter = new QEnter("enter");

    public final com.flab.quicktogether.member.domain.QMember enterMember;

    public final EnumPath<com.flab.quicktogether.project.support.ProjectJoinStatus> enterStatus = createEnum("enterStatus", com.flab.quicktogether.project.support.ProjectJoinStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.flab.quicktogether.project.domain.QProject project;

    public QEnter(String variable) {
        this(Enter.class, forVariable(variable), INITS);
    }

    public QEnter(Path<? extends Enter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEnter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEnter(PathMetadata metadata, PathInits inits) {
        this(Enter.class, metadata, inits);
    }

    public QEnter(Class<? extends Enter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.enterMember = inits.isInitialized("enterMember") ? new com.flab.quicktogether.member.domain.QMember(forProperty("enterMember")) : null;
        this.project = inits.isInitialized("project") ? new com.flab.quicktogether.project.domain.QProject(forProperty("project"), inits.get("project")) : null;
    }

}

