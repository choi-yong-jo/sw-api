package com.sungwon.api.common.controller;

import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.common.dto.MenuDTO;
import com.sungwon.api.common.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Menu")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @Operation(summary = "메뉴조회")
    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllMenus() {
        ResponseDTO responseDTO = menuService.findAll();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "메뉴조회")
    @GetMapping(value = "/detail")
    public ResponseEntity<?> getMenuDetail(@RequestParam String menuId) {
        ResponseDTO responseDTO = menuService.getDetail(menuId);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "메뉴등록")
    @PostMapping
    public ResponseEntity<?> insertMenu(@RequestBody MenuDTO dto) {
        ResponseDTO responseDTO = menuService.insert(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "메뉴수정")
    @PatchMapping
    public ResponseEntity<?> updateMenu(@RequestBody MenuDTO dto) {
        ResponseDTO responseDTO = menuService.update(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "메뉴삭제")
    @DeleteMapping
    public ResponseEntity<?> deleteMenu(@RequestParam String menuId) {
        ResponseDTO responseDTO = menuService.delete(menuId);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
