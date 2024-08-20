package com.sungwon.api.common.entity;

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
@Comment("공통코드 테이블")
public class CommonCode extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    @Comment("공통코드 PK")
    private Long id;

    @Column(length = 10, nullable = false)
    @Comment("코드")
    private String code;

    @Column(length = 10, nullable = false)
    @Comment("그룹코드")
    private String groupCode;

    @Column(length = 20, nullable = false)
    @Comment("코드명")
    private String codeName;

    @Comment("코드설명")
    private String description;

    @Comment("코드 정렬순서")
    private int sortNo;

    @Comment("사용여부")
    private String useYn = "Y";
}
