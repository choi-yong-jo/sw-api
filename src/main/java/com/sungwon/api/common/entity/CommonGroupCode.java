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
@Comment("공통그룹코드 테이블")
public class CommonGroupCode extends AuditingAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("공통그룹코드 PK")
    private Long groupId;

    @Comment("그룹코드")
    private String groupCode;

    @Comment("그룹코드명")
    private String groupName;

    @Comment("그룹코드설명")
    private String description;

    @Comment("그룹코드 정렬순서")
    private int sortNo;

//    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
//    private List<CommonCode> commonCodes;

    @Comment("사용여부")
    private String useYn = "Y";
}
