package com.flab.quicktogether.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = 757932005L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final DateTimePath<java.time.LocalDateTime> createDateTime = createDateTime("createDateTime", java.time.LocalDateTime.class);

    public final com.flab.quicktogether.member.domain.QMember founder;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<MeetingMethod> meetingMethod = createEnum("meetingMethod", MeetingMethod.class);

    public final com.flab.quicktogether.participant.domain.QParticipants participants;

    public final DateTimePath<java.time.LocalDateTime> periodDateTime = createDateTime("periodDateTime", java.time.LocalDateTime.class);

    public final QProjectDescriptionInfo projectDescriptionInfo;

    public final StringPath projectName = createString("projectName");

    public final EnumPath<ProjectStatus> projectStatus = createEnum("projectStatus", ProjectStatus.class);

    public final ListPath<com.flab.quicktogether.common.Position, EnumPath<com.flab.quicktogether.common.Position>> RecruitmentPositions = this.<com.flab.quicktogether.common.Position, EnumPath<com.flab.quicktogether.common.Position>>createList("RecruitmentPositions", com.flab.quicktogether.common.Position.class, EnumPath.class, PathInits.DIRECT2);

    public final ListPath<com.flab.quicktogether.common.SkillStack, EnumPath<com.flab.quicktogether.common.SkillStack>> skillStacks = this.<com.flab.quicktogether.common.SkillStack, EnumPath<com.flab.quicktogether.common.SkillStack>>createList("skillStacks", com.flab.quicktogether.common.SkillStack.class, EnumPath.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> startDateTime = createDateTime("startDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.founder = inits.isInitialized("founder") ? new com.flab.quicktogether.member.domain.QMember(forProperty("founder")) : null;
        this.participants = inits.isInitialized("participants") ? new com.flab.quicktogether.participant.domain.QParticipants(forProperty("participants")) : null;
        this.projectDescriptionInfo = inits.isInitialized("projectDescriptionInfo") ? new QProjectDescriptionInfo(forProperty("projectDescriptionInfo")) : null;
    }

}

