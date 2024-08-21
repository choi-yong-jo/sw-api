package com.sungwon.api.common.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sungwon.api.board.entity.Board;
import com.sungwon.api.board.entity.QBoard;
import com.sungwon.api.common.utility.SearchCondition;
import com.sungwon.api.member.entity.Member;
import com.sungwon.api.member.entity.QMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class SearchRepository {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;
    QBoard b;
    QMember m;

    public Page<Board> findAllByBoard(Pageable pageable, SearchCondition searchCondition) {
        b = QBoard.board;
        queryFactory = new JPAQueryFactory(em);
        JPAQuery<Board> query = queryFactory.selectFrom(b)
                .where(searchBoardKeywords(searchCondition.getKey(), searchCondition.getValue()));

        long total = query.fetch().stream().count();   //여기서 전체 카운트 후 아래에서 조건작업

        List<Board> results = query
                .where(searchBoardKeywords(searchCondition.getKey(), searchCondition.getValue()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(b.boardSeq.desc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    public BooleanExpression searchBoardKeywords(String key, String value) {
        b = QBoard.board;
        if("memberId".equals(key)) {
            if(StringUtils.hasLength(value)) {
                return b.memberId.contains(value);
            }
        } else if ("title".equals(key)) {
            if(StringUtils.hasLength(value)) {
                return b.title.contains(value);
            }
        } else if ("contents".equals(key)) {
            if(StringUtils.hasLength(value)) {
                return b.contents.contains(value);
            }
        }

        return null;
    }

    public Page<Member> findAllByMember(Pageable pageable, SearchCondition searchCondition) {
        m = QMember.member;
        queryFactory = new JPAQueryFactory(em);
        JPAQuery<Member> query = queryFactory.selectFrom(m)
                .where(searchMemberKeywords(searchCondition.getKey(), searchCondition.getValue()), searchMemberKeywords("useYn", "Y"))
                ;

        long total = query.fetch().stream().count();   //여기서 전체 카운트 후 아래에서 조건작업

        List<Member> results = query
                .where(searchMemberKeywords(searchCondition.getKey(), searchCondition.getValue()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(m.memberSeq.desc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    public BooleanExpression searchMemberKeywords(String key, String value) {
        m = QMember.member;
        if("useYn".equals(key)) {
            if(StringUtils.hasLength(value)) {
                return m.useYn.contains(value);
            }
        } else if ("memberId".equals(key)) {
            if(StringUtils.hasLength(value)) {
                return m.memberId.contains(value);
            }
        } else if ("teamId".equals(key)) {
            if(StringUtils.hasLength(value)) {
                return m.teamId.contains(value);
            }
        }

        return null;
    }

}
