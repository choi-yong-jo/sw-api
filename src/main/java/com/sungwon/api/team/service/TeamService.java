package com.sungwon.api.team.service;

import com.sungwon.api.common.constant.ResultCode;
import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.team.repository.TeamRepository;
import com.sungwon.api.team.dto.TeamDTO;
import com.sungwon.api.team.entity.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findByUseYnOrderByTeamSeqDesc("Y").forEach(e -> teams.add((Team) e));
        return teams;
    }

    public ResponseDTO insert(TeamDTO dto) {
        ResponseDTO responseDTO = new ResponseDTO();
        Team team = new Team();
        BeanUtils.copyProperties(dto, team);
        teamRepository.save(team);
        responseDTO.setResultCode(ResultCode.INSERT.getName());
        responseDTO.setMsg(ResultCode.INSERT.getValue());
        
        return responseDTO;
    }

    public ResponseDTO update(Long teamSeq, TeamDTO dto) {
        Optional<Team> t = teamRepository.findById(teamSeq);
        ResponseDTO responseDTO = new ResponseDTO();
        if(t.isPresent()) {
            Team team = new Team();
            BeanUtils.copyProperties(dto, team);
            team.setTeamSeq(teamSeq);
            teamRepository.save(team);
            responseDTO.setResultCode(ResultCode.UPDATE.getName());
            responseDTO.setMsg(ResultCode.UPDATE.getValue());
        } else {
            responseDTO.setResultCode(ResultCode.NOT_FOUND_INFO.getName());
            responseDTO.setMsg(ResultCode.NOT_FOUND_INFO.getValue());
        }

        return responseDTO;
    }

    public ResponseDTO delete(Long teamSeq) {
        Optional<Team> t = teamRepository.findById(teamSeq);
        ResponseDTO responseDTO = new ResponseDTO();
        if(t.isPresent()) {
            Team team = t.get();
            team.setUseYn("N");
            teamRepository.save(team);
            responseDTO.setResultCode(ResultCode.DELETE.getName());
            responseDTO.setMsg(ResultCode.DELETE.getValue());
        } else {
            responseDTO.setResultCode(ResultCode.NOT_FOUND_INFO.getName());
            responseDTO.setMsg(ResultCode.NOT_FOUND_INFO.getValue());
        }

        return responseDTO;
    }
}
