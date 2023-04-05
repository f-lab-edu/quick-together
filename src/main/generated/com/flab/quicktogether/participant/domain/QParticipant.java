package com.flab.quicktogether.participant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipant is a Querydsl query type for Participant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipant extends EntityPathBase<Participant> {

    private static final long serialVersionUID = 1869489957L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipant participant = new QParticipant("participant");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.flab.quicktogether.member.domain.QMember member;

    public final EnumPath<ParticipantRole> participantRole = createEnum("participantRole", ParticipantRole.class);

    public final SetPath<com.flab.quicktogether.common.Position, EnumPath<com.flab.quicktogether.common.Position>> positions = this.<com.flab.quicktogether.common.Position, EnumPath<com.flab.quicktogether.common.Position>>createSet("positions", com.flab.quicktogether.common.Position.class, EnumPath.class, PathInits.DIRECT2);

    public final com.flab.quicktogether.project.domain.QProject project;

    public final SetPath<com.flab.quicktogether.common.SkillStack, EnumPath<com.flab.quicktogether.common.SkillStack>> skillStacks = this.<com.flab.quicktogether.common.SkillStack, EnumPath<com.flab.quicktogether.common.SkillStack>>createSet("skillStacks", com.flab.quicktogether.common.SkillStack.class, EnumPath.class, PathInits.DIRECT2);

    public QParticipant(String variable) {
        this(Participant.class, forVariable(variable), INITS);
    }

    public QParticipant(Path<? extends Participant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipant(PathMetadata metadata, PathInits inits) {
        this(Participant.class, metadata, inits);
    }

    public QParticipant(Class<? extends Participant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.flab.quicktogether.member.domain.QMember(forProperty("member")) : null;
        this.project = inits.isInitialized("project") ? new com.flab.quicktogether.project.domain.QProject(forProperty("project"), inits.get("project")) : null;
    }

}

