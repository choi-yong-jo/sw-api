package com.sungwon.api.common.repository;

import com.sungwon.api.common.repository.GenericRepository;
import com.sungwon.api.common.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends GenericRepository<Menu> {


    Iterable<Object> findByUseYn(String y);

    Iterable<Object> findByUpMenuId(String upMenuId);

    List<Menu> findByMenuId(String menuId);
}
