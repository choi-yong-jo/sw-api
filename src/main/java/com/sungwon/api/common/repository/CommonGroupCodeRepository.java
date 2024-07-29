package com.sungwon.api.common.repository;

import com.sungwon.api.common.entity.CommonGroupCode;

import java.util.List;

public interface CommonGroupCodeRepository extends GenericRepository<CommonGroupCode> {
    Iterable<Object> findByUseYn(String y);

    List<CommonGroupCode> findByGroupCode(String groupCode);
}
