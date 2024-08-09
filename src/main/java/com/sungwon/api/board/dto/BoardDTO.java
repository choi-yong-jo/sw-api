package com.sungwon.api.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO implements Serializable {

    private Long boardSeq;

    private String boardType;

    private String title;

    private String contents;

    private String memberId;

//    private List<File> fileList;

    private Long hit;

    private Timestamp createdAt;

}
