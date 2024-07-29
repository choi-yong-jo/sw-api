package com.sungwon.api.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditingAt is a Querydsl query type for AuditingAt
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAuditingAt extends EntityPathBase<AuditingAt> {

    private static final long serialVersionUID = -1373031914L;

    public static final QAuditingAt auditingAt = new QAuditingAt("auditingAt");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> updatedAt = createDateTime("updatedAt", java.sql.Timestamp.class);

    public QAuditingAt(String variable) {
        super(AuditingAt.class, forVariable(variable));
    }

    public QAuditingAt(Path<? extends AuditingAt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditingAt(PathMetadata metadata) {
        super(AuditingAt.class, metadata);
    }

}

