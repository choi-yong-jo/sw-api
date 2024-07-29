package com.sungwon.api.common.entity;

import com.sungwon.api.common.entity.AuditingAt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Comment;

@Data
@Entity
@Table(schema = "admin")
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
