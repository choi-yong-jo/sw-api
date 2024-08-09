package com.sungwon.api.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -1671248489L;

    public static final QBoard board = new QBoard("board");

    public final com.sungwon.api.common.entity.QAuditingAt _super = new com.sungwon.api.common.entity.QAuditingAt(this);

    public final NumberPath<Long> boardSeq = createNumber("boardSeq", Long.class);

    public final StringPath boardType = createString("boardType");

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdAt = _super.createdAt;

    public final NumberPath<Long> hit = createNumber("hit", Long.class);

    public final StringPath memberId = createString("memberId");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.sql.Timestamp> updatedAt = _super.updatedAt;

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

