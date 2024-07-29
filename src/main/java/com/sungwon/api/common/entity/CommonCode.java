package com.sungwon.api.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(schema = "admin")
@Comment("공통코드 테이블")
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    @Comment("공통코드 PK")
    private Long id;

    @Comment("코드")
    private String codeCd;

    @Comment("그룹코드")
    private String codeGroupCd;

    @Comment("코드명")
    private String codeNm;

    @Comment("코드설명")
    private String description;

    @Comment("코드 정렬순서")
    private Integer sortNo;

    @Comment("사용여부")
    private String useYn = "Y";
}
