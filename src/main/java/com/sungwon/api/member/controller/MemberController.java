package com.sungwon.api.member.controller;

import com.sungwon.api.member.exception.MemberServiceException;
import com.sungwon.api.common.utility.Header;
import com.sungwon.api.common.utility.SearchCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sungwon.api.common.constant.ResultCode;
import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.common.utility.SHA256;
import com.sungwon.api.member.dto.request.LoginDTO;
import com.sungwon.api.member.dto.request.MemberDTO;
import com.sungwon.api.member.dto.request.MemberRoleDTO;
import com.sungwon.api.member.dto.response.MemberDetailDTO;
import com.sungwon.api.member.entity.Member;
import com.sungwon.api.member.service.MemberService;
import com.sungwon.api.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Tag(name = "Member")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/member")
public class MemberController {

    @Autowired
    MemberService memberService;
    
    @Autowired
    private MemberRoleRepository memberRoleRepository;
    

    @Operation(summary = "회원조회")
    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllmembers() {
        ResponseDTO responseDTO = memberService.findAll();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "회원조회")
    @GetMapping(value = "/list2")
    public Header<List<MemberDTO>> memberList(@PageableDefault(sort = {"memberSeq"}) Pageable pageable, SearchCondition searchCondition) {
        return memberService.getMemberList(pageable, searchCondition);
    }

    @Operation(summary = "로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) throws NoSuchAlgorithmException {
        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        ResponseDTO responseDTO = memberService.login(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "회원상세조회")
    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<?> getMember(@PathVariable("id") Long memberSeq){
        ResponseDTO responseDTO = memberService.getMemberDetail(memberSeq);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "회원등록(회원권한등록)")
    @PostMapping
    @Transactional("transactionManager")
    public ResponseEntity<?> insertMember(@RequestBody MemberDTO dto) throws NoSuchAlgorithmException {
        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        ResponseDTO responseDTO = memberService.insert(dto);
        if(dto.getRoles() != null && responseDTO.getResultCode().equals("INSERT")) {
            MemberRoleDTO mr = new MemberRoleDTO();
            Member m = (Member) responseDTO.getRes();
            mr.setMemberSeq(m.getMemberSeq());
            mr.setRoles(dto.getRoles());
            ResponseDTO mappingResult = memberService.insertMemberRole(mr);
            if(!mappingResult.getResultCode().equals("SUCCESS")) {
                responseDTO.setResultCode(mappingResult.getResultCode());
                responseDTO.setMsg(mappingResult.getMsg());
                responseDTO.setRes(null);
                throw new MemberServiceException(responseDTO); // 예외 발생
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "회원수정(기존 회원권한삭제 및 회원권한등록)")
    @PatchMapping(value = "/{id}")
    @Transactional("transactionManager")
    public ResponseEntity<?> updateMember(@PathVariable("id") Long memberSeq ,@RequestBody MemberDTO dto) throws NoSuchAlgorithmException {
        dto.setPassword(SHA256.encrypt(dto.getPassword()));
        ResponseDTO responseDTO = memberService.getMemberDetail(memberSeq);
        List<MemberDetailDTO> mrList = (List<MemberDetailDTO>) responseDTO.getRes();
        HashMap<String, String> map = mrList.get(0);
        String roles = map.get("roles").replace("|",",").trim();
        if(dto.getRoles() != null && !dto.getRoles().equals(roles)) {
            memberRoleRepository.deleteByMemberSeq(memberSeq);
            MemberRoleDTO mr = new MemberRoleDTO();
            mr.setMemberSeq(memberSeq);
            mr.setRoles(dto.getRoles());
            responseDTO = memberService.insertMemberRole(mr);
        }

        if(responseDTO.getResultCode().equals("SUCCESS")) {
            responseDTO = memberService.update(memberSeq, dto);
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "회원삭제")
    @DeleteMapping(value = "/{id}")
    @Transactional("transactionManager")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long memberSeq) throws NoSuchAlgorithmException {
        ResponseDTO responseDTO = memberService.delete(memberSeq);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "회원권한조회")
    @GetMapping(value = "/mapping")
    public ResponseEntity<?> insertMemberRole(@RequestParam("memberSeq") Long memberSeq) {
        ResponseDTO responseDTO = memberService.mappingById(memberSeq);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "회원권한등록")
    @PostMapping(value = "/mapping")
    public ResponseEntity<?> insertMemberRole(@RequestBody MemberRoleDTO dto) throws NoSuchAlgorithmException {
        Optional<Member> m = memberService.findById(dto.getMemberSeq());
        ResponseDTO responseDTO = new ResponseDTO();
        if(m.isPresent()){
            responseDTO = memberService.updateMemberRole(dto);
        } else {
            responseDTO.setResultCode(ResultCode.NOT_FOUND_INFO.getName());
            responseDTO.setMsg(ResultCode.NOT_FOUND_INFO.getValue());
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "회원권한삭제")
    @DeleteMapping(value = "/mapping/{id}")
    public ResponseEntity<?> deleteMemberRole(@PathVariable("id") MemberRoleDTO dto) throws NoSuchAlgorithmException {
        Optional<Member> m = memberService.findById(dto.getMemberSeq());
        ResponseDTO responseDTO = new ResponseDTO();
        if(m.isPresent()){
            responseDTO = memberService.deleteMemberRole(dto);
        } else {
            responseDTO.setResultCode(ResultCode.NOT_FOUND_INFO.getName());
            responseDTO.setMsg(ResultCode.NOT_FOUND_INFO.getValue());
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
