package com.sungwon.api.common.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sungwon.api.common.constant.ResultCode;
import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.common.dto.MenuDTO;
import com.sungwon.api.common.entity.Menu;
import com.sungwon.api.common.entity.QMenu;
import com.sungwon.api.common.repository.MenuRepository;
import com.sungwon.api.common.utility.CommonUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    CommonUtil commonUtil;

    JPAQueryFactory queryFactory;
    QMenu m;

    public ResponseDTO findAll() {
        List<Menu> list = new ArrayList<>();
        menuRepository.findByUseYn("Y").forEach(e -> list.add((Menu) e));
        ResponseDTO responseDTO = commonUtil.selectObject(list);

        return responseDTO;
    }

    private ResponseDTO searchInfo(String upMenuId) {
        List<Menu> list = new ArrayList<>();
        menuRepository.findByUpMenuId(upMenuId).forEach(e -> list.add((Menu) e));
        ResponseDTO responseDTO = commonUtil.selectObject(list);

        return responseDTO;
    }

    public ResponseDTO getDetail(String menuId){
        List<Tuple> info;
        m = QMenu.menu;
        QMenu up_m = QMenu.menu;
        queryFactory = new JPAQueryFactory(em);
        info = queryFactory.from(m)
                .select(m.menuId
                        , m.menuNm
                        , select(up_m.menuNm).from(up_m).where(up_m.menuId.eq(m.upMenuId))
                )
                .where(m.menuId.eq(menuId))
                .fetch().stream().toList();

        String[] column = {"menuId", "menuNm", "upMenuNm"};
        ResponseDTO data = commonUtil.convertJsonArray(column, info);
        return data;
    }

    public Optional<Menu> findById(String menuId){
        Optional<Menu> menu;
        m = QMenu.menu;
        queryFactory = new JPAQueryFactory(em);
        menu = Optional.ofNullable(
                queryFactory.selectFrom(m)
                .where(m.menuId.eq(menuId)).fetchOne()
        );

        return menu;
    }

    @Transactional("transactionManager")
    public ResponseDTO insert(MenuDTO dto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(dto, menu);
        ResponseDTO responseDTO = validationCheck(menu, "I");
        if ("".equals(responseDTO.getResultCode()) || responseDTO.getResultCode() == null) {
            menuRepository.save(menu);
            System.out.println("menu.getMenuId() = " + menu.getMenuId());
            responseDTO.setResultCode(ResultCode.INSERT.getName());
            responseDTO.setMsg(ResultCode.INSERT.getValue());
            responseDTO.setRes(menu);
        }
        return responseDTO;
    }

    @Transactional("transactionManager")
    public ResponseDTO update(MenuDTO dto) {
        List<Menu> m = menuRepository.findByMenuId(dto.getMenuId());
        ResponseDTO responseDTO = new ResponseDTO();
        if(m.isEmpty()) {
            responseDTO.setResultCode(ResultCode.NOT_FOUND_MENU.getName());
            responseDTO.setMsg(ResultCode.NOT_FOUND_MENU.getValue());
        } else {
            Menu menu = new Menu();
            BeanUtils.copyProperties(dto, menu);
            responseDTO = validationCheck(menu, "U");
            if ("".equals(responseDTO.getResultCode()) || responseDTO.getResultCode() == null) {
                menuRepository.save(menu);
                responseDTO.setResultCode(ResultCode.UPDATE.getName());
                responseDTO.setMsg(ResultCode.UPDATE.getValue());
                responseDTO.setRes(menu);
            }
        }

        return responseDTO;
    }

    @Transactional("transactionManager")
    public ResponseDTO delete(String menuId) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<Menu> m = menuRepository.findByMenuId(menuId);
        if(m.isEmpty()) {
            responseDTO.setResultCode(ResultCode.NOT_FOUND_MENU.getName());
            responseDTO.setMsg(ResultCode.NOT_FOUND_MENU.getValue());
        } else {
            Menu menu = m.get(0);
            menu.setUseYn("N");
            menuRepository.save(menu);
            responseDTO.setResultCode(ResultCode.DELETE.getName());
            responseDTO.setMsg(ResultCode.DELETE.getValue());
            responseDTO.setRes(menu);
        }

        return responseDTO;
    }

    private ResponseDTO validationCheck(Menu menu, String type) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<Menu> list = menuRepository.findByMenuId(menu.getMenuId()).stream().toList();
        if ("I".equals(type) && list.size() > 0) {
            responseDTO.setResultCode(ResultCode.NOT_INSERT_SAME_MENU_ID.getName());
            responseDTO.setMsg(ResultCode.NOT_INSERT_SAME_MEMBER_ID.getValue());
        }

        return responseDTO;
    }

}
