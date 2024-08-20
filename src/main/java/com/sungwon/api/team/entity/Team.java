package com.sungwon.api.team.entity;

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
@Table(schema = "admin")
@Entity
@Comment("부서 테이블")
public class Team extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamSeq;

    @Column(length = 10, nullable = false)
    @Comment("부서ID")
    private String teamId;

    @Column(length = 20, nullable = false)
    @Comment("부서명")
    private String teamNm;

    @Column(columnDefinition = "char", length = 1, nullable = false)
    @Comment("사용여부")
    private String useYn = "Y";

    @Comment("부서 설명")
    private String description;

}
