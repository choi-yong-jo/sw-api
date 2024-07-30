package com.sungwon.api.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = -612633343L;

    public static final QRole role = new QRole("role");

    public final com.sungwon.api.common.entity.QAuditingAt _super = new com.sungwon.api.common.entity.QAuditingAt(this);

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final StringPath name = createString("name");

    public final StringPath roleId = createString("roleId");

    public final NumberPath<Long> roleSeq = createNumber("roleSeq", Long.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> updatedAt = _super.updatedAt;

    public final StringPath useYn = createString("useYn");

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata metadata) {
        super(Role.class, metadata);
    }

}

