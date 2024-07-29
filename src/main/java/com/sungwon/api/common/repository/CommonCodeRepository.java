package com.sungwon.api.common.repository;

import com.sungwon.api.common.entity.CommonCode;

import java.util.List;

public interface CommonCodeRepository extends GenericRepository<CommonCode> {
    List<CommonCode> findByGroupCode(String groupCode);
}
