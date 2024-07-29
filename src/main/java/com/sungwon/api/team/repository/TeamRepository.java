package com.sungwon.api.team.repository;

import com.sungwon.api.common.repository.GenericRepository;
import com.sungwon.api.team.entity.Team;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends GenericRepository<Team> {

    Iterable<Object> findByUseYnOrderByTeamSeqDesc(String useYn);
}
