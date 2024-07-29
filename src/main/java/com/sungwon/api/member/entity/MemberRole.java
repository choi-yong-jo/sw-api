package com.sungwon.api.member.entity;

import jakarta.persistence.*;
import com.sungwon.api.common.entity.AuditingAt;
import lombok.Data;

@Entity
@Data
@Table(schema = "admin")
public class MemberRole extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberRoleSeq;
    private Long memberSeq;
    private Long roleSeq;

}
