package com.flab.quicktogether.timeplan.domain.plan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlanApiIntegrationInfo is a Querydsl query type for PlanApiIntegrationInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlanApiIntegrationInfo extends EntityPathBase<PlanApiIntegrationInfo> {

    private static final long serialVersionUID = 65682435L;

    public static final QPlanApiIntegrationInfo planApiIntegrationInfo = new QPlanApiIntegrationInfo("planApiIntegrationInfo");

    public final EnumPath<PlanApiIntegrationInfo.Api> api = createEnum("api", PlanApiIntegrationInfo.Api.class);

    public final StringPath authToken = createString("authToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QPlanApiIntegrationInfo(String variable) {
        super(PlanApiIntegrationInfo.class, forVariable(variable));
    }

    public QPlanApiIntegrationInfo(Path<? extends PlanApiIntegrationInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlanApiIntegrationInfo(PathMetadata metadata) {
        super(PlanApiIntegrationInfo.class, metadata);
    }

}

