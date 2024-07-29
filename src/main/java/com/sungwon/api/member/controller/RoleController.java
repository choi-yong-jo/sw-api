package com.sungwon.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.common.utility.CommonUtil;
import com.sungwon.api.member.dto.request.RoleDTO;
import com.sungwon.api.member.entity.Role;
import com.sungwon.api.member.service.MemberService;
import com.sungwon.api.member.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Tag(name = "Role")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/role")
public class RoleController {

    @Autowired
    MemberService memberService;

    @Autowired
    RoleService roleService;

    @Autowired
    CommonUtil commonUtil;

    @Operation(summary = "권한조회")
    @GetMapping(value = "/list")
    public ResponseEntity<?> getRoles() {
        List<Role> list = roleService.findAll();
        ResponseDTO responseDTO = commonUtil.selectObject(list);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "권한등록")
    @PostMapping
    public ResponseEntity<?> insertRole(@RequestBody RoleDTO dto) {
        ResponseDTO responseDTO = roleService.insert(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "권한수정")
    @PatchMapping
    public ResponseEntity<?> updateRole(@RequestParam("roleSeq") Long roleSeq, @RequestBody RoleDTO dto) {
        ResponseDTO responseDTO = roleService.update(roleSeq, dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "권한삭제")
    @DeleteMapping
    public ResponseEntity<?> deleteRole(@RequestParam("roleSeq") Long roleSeq) throws NoSuchAlgorithmException {
        ResponseDTO responseDTO = roleService.delete(roleSeq);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
