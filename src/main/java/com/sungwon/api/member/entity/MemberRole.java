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
@Comment("회원-권한 테이블")
public class MemberRole extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberRoleSeq;
    private Long memberSeq;
    private Long roleSeq;

}
