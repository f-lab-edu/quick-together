package com.flab.quicktogether.meeting.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeetingParticipant is a Querydsl query type for MeetingParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMeetingParticipant extends EntityPathBase<MeetingParticipant> {

    private static final long serialVersionUID = 1362105934L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeetingParticipant meetingParticipant = new QMeetingParticipant("meetingParticipant");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.flab.quicktogether.member.domain.QMember member;

    public final EnumPath<com.flab.quicktogether.participant.domain.ParticipantRole> participantRole = createEnum("participantRole", com.flab.quicktogether.participant.domain.ParticipantRole.class);

    public QMeetingParticipant(String variable) {
        this(MeetingParticipant.class, forVariable(variable), INITS);
    }

    public QMeetingParticipant(Path<? extends MeetingParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeetingParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeetingParticipant(PathMetadata metadata, PathInits inits) {
        this(MeetingParticipant.class, metadata, inits);
    }

    public QMeetingParticipant(Class<? extends MeetingParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.flab.quicktogether.member.domain.QMember(forProperty("member")) : null;
    }

}

