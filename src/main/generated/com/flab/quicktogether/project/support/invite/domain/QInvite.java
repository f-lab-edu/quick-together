package com.flab.quicktogether.project.support.invite.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvite is a Querydsl query type for Invite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvite extends EntityPathBase<Invite> {

    private static final long serialVersionUID = 879431619L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvite invite = new QInvite("invite");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.flab.quicktogether.member.domain.QMember invitedMember;

    public final EnumPath<com.flab.quicktogether.project.support.ProjectJoinStatus> inviteStatus = createEnum("inviteStatus", com.flab.quicktogether.project.support.ProjectJoinStatus.class);

    public final com.flab.quicktogether.project.domain.QProject project;

    public final com.flab.quicktogether.member.domain.QMember requestMember;

    public QInvite(String variable) {
        this(Invite.class, forVariable(variable), INITS);
    }

    public QInvite(Path<? extends Invite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvite(PathMetadata metadata, PathInits inits) {
        this(Invite.class, metadata, inits);
    }

    public QInvite(Class<? extends Invite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.invitedMember = inits.isInitialized("invitedMember") ? new com.flab.quicktogether.member.domain.QMember(forProperty("invitedMember")) : null;
        this.project = inits.isInitialized("project") ? new com.flab.quicktogether.project.domain.QProject(forProperty("project"), inits.get("project")) : null;
        this.requestMember = inits.isInitialized("requestMember") ? new com.flab.quicktogether.member.domain.QMember(forProperty("requestMember")) : null;
    }

}

