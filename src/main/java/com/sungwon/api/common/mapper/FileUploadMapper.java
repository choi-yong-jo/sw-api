package com.sungwon.api.common.mapper;

import com.sungwon.api.common.entity.File;

import java.util.List;

public interface FileUploadMapper {

    public void insertFile(File file);

    public void deleteFile(Long fileId);

    public List<File> findByBoardSeq(Long boardSeq);

}
