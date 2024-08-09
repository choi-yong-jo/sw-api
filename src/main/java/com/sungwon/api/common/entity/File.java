package com.sungwon.api.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Data
@NoArgsConstructor
@Table(schema = "admin")
@Entity
@Comment("첨부파일 테이블")
public class File extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String uuid;

    private String filePath;

    private String fileName;

    private String fileType;

    private String uploadPath;

    private Long attachSeq;

    private String memberId;

}
