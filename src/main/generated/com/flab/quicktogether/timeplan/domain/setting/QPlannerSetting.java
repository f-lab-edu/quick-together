package com.flab.quicktogether.timeplan.domain.setting;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlannerSetting is a Querydsl query type for PlannerSetting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlannerSetting extends EntityPathBase<PlannerSetting> {

    private static final long serialVersionUID = -1763709393L;

    public static final QPlannerSetting plannerSetting = new QPlannerSetting("plannerSetting");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> limit = createNumber("limit", Integer.class);

    public final NumberPath<Integer> marginalMinutes = createNumber("marginalMinutes", Integer.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final EnumPath<MinuteUnit> minuteUnit = createEnum("minuteUnit", MinuteUnit.class);

    public final EnumPath<SequencePriority> sequencePriority = createEnum("sequencePriority", SequencePriority.class);

    public QPlannerSetting(String variable) {
        super(PlannerSetting.class, forVariable(variable));
    }

    public QPlannerSetting(Path<? extends PlannerSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlannerSetting(PathMetadata metadata) {
        super(PlannerSetting.class, metadata);
    }

}

