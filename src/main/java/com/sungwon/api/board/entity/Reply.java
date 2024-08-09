package com.sungwon.api.board.entity;

import com.sungwon.api.common.entity.AuditingAt;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;

@Entity
@Data
@Table(schema = "admin")
@Comment("댓글 테이블")
public class Reply extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Replyseq;

    private Long boardSeq;

    private String content;

    private String memberId;

}
