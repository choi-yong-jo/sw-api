package com.sungwon.api.member.entity;

import jakarta.persistence.*;
import com.sungwon.api.common.entity.AuditingAt;
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
@Comment("권한 테이블")
public class Role extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleSeq;

    @Column(length = 10, nullable = false)
    @Comment("권한ID")
    private String roleId;

    @Column(name = "role_nm", length = 20, nullable = false)
    @Comment("권한명")
    private String name;

    @Column(columnDefinition = "char", length = 1, nullable = false)
    @Comment("사용여부")
    private String useYn = "Y";

    @Comment("권한 부가 설명")
    private String description;

}
