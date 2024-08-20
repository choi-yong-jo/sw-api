package com.sungwon.api.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonCode is a Querydsl query type for CommonCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommonCode extends EntityPathBase<CommonCode> {

    private static final long serialVersionUID = -1890595884L;

    public static final QCommonCode commonCode = new QCommonCode("commonCode");

    public final QAuditingAt _super = new QAuditingAt(this);

    public final StringPath code = createString("code");

    public final StringPath codeName = createString("codeName");

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final StringPath groupCode = createString("groupCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> sortNo = createNumber("sortNo", Integer.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> updatedAt = _super.updatedAt;

    public final StringPath useYn = createString("useYn");

    public QCommonCode(String variable) {
        super(CommonCode.class, forVariable(variable));
    }

    public QCommonCode(Path<? extends CommonCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonCode(PathMetadata metadata) {
        super(CommonCode.class, metadata);
    }

}

