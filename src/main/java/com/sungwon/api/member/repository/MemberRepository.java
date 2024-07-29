package com.sungwon.api.member.repository;

import com.sungwon.api.member.entity.Member;
import com.sungwon.api.common.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends GenericRepository<Member> {

    Optional<Member> findByEmail(String memberId);

    Integer countAllByEmail(String email);

    Integer countAllByMobile(String mobile);

    Optional<Member> findByNameAndMobile(String name, String mobile);

    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByEmailAndName(String email, String name);

    Iterable<Object> findByUseYn(String useYn);

    Optional<Member> findByMemberSeq(Long memberSeq);
}
