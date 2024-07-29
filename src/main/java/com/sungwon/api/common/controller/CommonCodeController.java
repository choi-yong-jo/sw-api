package com.sungwon.api.common.controller;

import com.sungwon.api.common.dto.CommonCodeDTO;
import com.sungwon.api.common.dto.CommonGroupCodeDTO;
import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.common.service.CommonCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "commonCode")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/common/")
public class CommonCodeController {

    @Autowired
    CommonCodeService commonCodeService;

    @Operation(summary = "공통그룹코드조회")
    @GetMapping(value = "/group/list")
    public ResponseEntity<?> getAllCommonGroupCode() {
        ResponseDTO responseDTO = commonCodeService.findAllCommonGroupCode();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "공통그룹코드등록")
    @PostMapping(value = "/group")
    public ResponseEntity<?> insertCommonGroupCode(@RequestBody CommonGroupCodeDTO dto) {
        ResponseDTO responseDTO = commonCodeService.insertCommonGroupCode(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "공통그룹코드수정")
    @PatchMapping(value = "/group")
    public ResponseEntity<?> modifyCommonGroupCode(@RequestParam Long groupId, @RequestBody CommonGroupCodeDTO dto) {
        ResponseDTO responseDTO = commonCodeService.modifyCommonGroupCode(groupId, dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "공통그룹코드삭제")
    @DeleteMapping(value = "/group")
    public ResponseEntity<?> removeCommonGroupCode(@RequestParam Long groupId) {
        ResponseDTO responseDTO = commonCodeService.removeCommonGroupCode(groupId);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "공통코드조회")
    @GetMapping(value = "/list")
    public ResponseEntity<?> getCommonCode(@RequestParam String groupCode) {
        ResponseDTO responseDTO = commonCodeService.findCommonCode(groupCode);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "공통코드등록")
    @PostMapping
    public ResponseEntity<?> insertCommonCode(@RequestBody CommonCodeDTO dto) {
        ResponseDTO responseDTO = commonCodeService.insertCommonCode(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "공통코드수정")
    @PutMapping
    public ResponseEntity<?> modifyCommonCode(@RequestParam Long codeId, @RequestBody CommonCodeDTO dto) {
        ResponseDTO responseDTO = commonCodeService.modifyCommonCode(codeId, dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "공통코드삭제")
    @DeleteMapping
    public ResponseEntity<?> removeCommonCode(@RequestParam Long codeId) {
        ResponseDTO responseDTO = commonCodeService.removeCommonCode(codeId);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
