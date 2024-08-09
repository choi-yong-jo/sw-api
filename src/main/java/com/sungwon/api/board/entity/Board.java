package com.sungwon.api.board.entity;

import com.sungwon.api.common.entity.AuditingAt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "admin")
@Entity
@Comment("게시물 테이블")
public class Board extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardSeq;

    @Comment("게시물 타입")
    private String boardType;

    @Column(length = 100, nullable = false)
    @Comment("제목")
    private String title;

    @Lob
    @Comment("내용")
    private String contents;

    @Column(length = 20, nullable = true)
    @Comment("작성한 회원ID")
    private String memberId;

//    @OneToMany
//    @Nullable
//    @Comment("첨부파일")
//    private List<File> fileList;

    @Comment("조회수")
    private Long hit = 0L;

}
