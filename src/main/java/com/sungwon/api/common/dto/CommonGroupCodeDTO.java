package com.sungwon.api.common.dto;

import lombok.Data;

@Data
public class CommonGroupCodeDTO {

    private String groupCode;

    private String groupName;

    private String description;

    private Integer sortNo;
}
