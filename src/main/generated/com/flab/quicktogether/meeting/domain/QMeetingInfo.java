package com.flab.quicktogether.meeting.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeetingInfo is a Querydsl query type for MeetingInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMeetingInfo extends BeanPath<MeetingInfo> {

    private static final long serialVersionUID = -957139469L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeetingInfo meetingInfo = new QMeetingInfo("meetingInfo");

    public final StringPath description = createString("description");

    public final com.flab.quicktogether.timeplan.domain.value_type.QTimeBlock suggestionTime;

    public final StringPath title = createString("title");

    public QMeetingInfo(String variable) {
        this(MeetingInfo.class, forVariable(variable), INITS);
    }

    public QMeetingInfo(Path<? extends MeetingInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeetingInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeetingInfo(PathMetadata metadata, PathInits inits) {
        this(MeetingInfo.class, metadata, inits);
    }

    public QMeetingInfo(Class<? extends MeetingInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.suggestionTime = inits.isInitialized("suggestionTime") ? new com.flab.quicktogether.timeplan.domain.value_type.QTimeBlock(forProperty("suggestionTime")) : null;
    }

}

