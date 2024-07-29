package com.sungwon.api.member.repository;

import com.sungwon.api.member.entity.MemberRole;
import com.sungwon.api.common.repository.GenericRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRoleRepository extends GenericRepository<MemberRole>, QuerydslPredicateExecutor<MemberRole> {
    List<MemberRole> findByMemberSeqOrderByRoleSeq(Long memberSeq);
    Optional<MemberRole> findByMemberSeqAndRoleSeq(long memberSeq, long roleSeq);

    void deleteByMemberSeq(Long memberSeq);
}
