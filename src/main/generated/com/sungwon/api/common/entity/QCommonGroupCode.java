package com.sungwon.api.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonGroupCode is a Querydsl query type for CommonGroupCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommonGroupCode extends EntityPathBase<CommonGroupCode> {

    private static final long serialVersionUID = 1808546693L;

    public static final QCommonGroupCode commonGroupCode = new QCommonGroupCode("commonGroupCode");

    public final QAuditingAt _super = new QAuditingAt(this);

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final StringPath groupCode = createString("groupCode");

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final StringPath groupName = createString("groupName");

    public final NumberPath<Integer> sortNo = createNumber("sortNo", Integer.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> updatedAt = _super.updatedAt;

    public final StringPath useYn = createString("useYn");

    public QCommonGroupCode(String variable) {
        super(CommonGroupCode.class, forVariable(variable));
    }

    public QCommonGroupCode(Path<? extends CommonGroupCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonGroupCode(PathMetadata metadata) {
        super(CommonGroupCode.class, metadata);
    }

}

