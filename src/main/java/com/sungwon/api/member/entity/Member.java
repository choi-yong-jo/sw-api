package com.sungwon.api.member.entity;

import jakarta.persistence.*;
import com.sungwon.api.common.entity.AuditingAt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "admin")
@Entity
@Comment("회원 테이블")
public class Member extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    @Column(length = 20, nullable = false)
    @Comment("회원ID")
    private String memberId;

    @Column(length = 20, nullable = true)
    @Comment("부서ID")
    private String teamId;

    @Column(name = "member_nm", length = 20, nullable = false)
    @Comment("회원명")
    private String name;

    @Column(name = "member_pw", nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(length = 50, nullable = true)
    @Comment("이메일")
    private String email;

    @Column(length = 20, nullable = false)
    @Comment("전화번호")
    private String mobile;

    @Column(columnDefinition = "character", length = 1)
    @Comment("사용여부")
    private String useYn = "Y";

    private String refreshToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(schema = "admin", name = "memberRole", joinColumns = @JoinColumn(name = "memberSeq"), inverseJoinColumns = @JoinColumn(name = "roleSeq"))
    private List<Role> roles;

}