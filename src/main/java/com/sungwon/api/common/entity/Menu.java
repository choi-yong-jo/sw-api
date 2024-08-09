package com.sungwon.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Comment("메뉴 테이블")
public class Menu extends AuditingAt {
    @Id
    @Column(length = 20, nullable = false)
    @Comment("메뉴ID")
    private String menuId;

    @Column(length = 20, nullable = true)
    @Comment("상위메뉴ID")
    private String upMenuId;

    @Column(length = 20, nullable = false)
    @Comment("메뉴명")
    private String menuNm;

    @Column(length = 100, nullable = false)
    @Comment("URL주소")
    private String menuUrl;

    @Column(columnDefinition = "character", length = 1)
    @Comment("전시여부")
    private String displayYn = "Y";

    @Column(columnDefinition = "character", length = 1)
    @Comment("사용여부")
    private String useYn = "Y";
}
