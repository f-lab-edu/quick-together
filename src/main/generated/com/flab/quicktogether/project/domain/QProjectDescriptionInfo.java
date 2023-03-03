package com.flab.quicktogether.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProjectDescriptionInfo is a Querydsl query type for ProjectDescriptionInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProjectDescriptionInfo extends BeanPath<ProjectDescriptionInfo> {

    private static final long serialVersionUID = -1545785307L;

    public static final QProjectDescriptionInfo projectDescriptionInfo = new QProjectDescriptionInfo("projectDescriptionInfo");

    public final StringPath projectDescription = createString("projectDescription");

    public final StringPath projectSummary = createString("projectSummary");

    public QProjectDescriptionInfo(String variable) {
        super(ProjectDescriptionInfo.class, forVariable(variable));
    }

    public QProjectDescriptionInfo(Path<? extends ProjectDescriptionInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectDescriptionInfo(PathMetadata metadata) {
        super(ProjectDescriptionInfo.class, metadata);
    }

}

