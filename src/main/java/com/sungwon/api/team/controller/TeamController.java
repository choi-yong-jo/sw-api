package com.sungwon.api.team.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.common.utility.CommonUtil;
import com.sungwon.api.team.dto.TeamDTO;
import com.sungwon.api.team.entity.Team;
import com.sungwon.api.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Team")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    CommonUtil commonUtil;

    @Operation(summary = "부서조회")
    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllTeams() {
        List<Team> member = teamService.findAll();
        ResponseDTO responseDTO = commonUtil.selectObject(member);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "부서등록")
    @PostMapping
    public ResponseEntity<?> insertTeam(@RequestBody TeamDTO dto) {
        ResponseDTO responseDTO = teamService.insert(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "부서수정")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable("id") Long teamSeq,@RequestBody TeamDTO dto) {
        ResponseDTO responseDTO = teamService.update(teamSeq, dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "부서삭제")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable("id") Long teamSeq) {
        ResponseDTO responseDTO = teamService.delete(teamSeq);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
