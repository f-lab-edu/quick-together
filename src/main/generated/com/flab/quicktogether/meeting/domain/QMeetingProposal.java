package com.flab.quicktogether.meeting.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeetingProposal is a Querydsl query type for MeetingProposal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMeetingProposal extends EntityPathBase<MeetingProposal> {

    private static final long serialVersionUID = -938470985L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeetingProposal meetingProposal = new QMeetingProposal("meetingProposal");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMeetingInfo meetingInfo;

    public final EnumPath<MeetingProposalStatus> status = createEnum("status", MeetingProposalStatus.class);

    public QMeetingProposal(String variable) {
        this(MeetingProposal.class, forVariable(variable), INITS);
    }

    public QMeetingProposal(Path<? extends MeetingProposal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeetingProposal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeetingProposal(PathMetadata metadata, PathInits inits) {
        this(MeetingProposal.class, metadata, inits);
    }

    public QMeetingProposal(Class<? extends MeetingProposal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.meetingInfo = inits.isInitialized("meetingInfo") ? new QMeetingInfo(forProperty("meetingInfo"), inits.get("meetingInfo")) : null;
    }

}

