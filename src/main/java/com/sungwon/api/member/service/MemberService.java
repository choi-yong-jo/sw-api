package com.sungwon.api.member.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sungwon.api.board.entity.Board;
import com.sungwon.api.common.repository.SearchRepository;
import com.sungwon.api.common.utility.*;
import com.sungwon.api.member.mapper.MemberMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.sungwon.api.common.constant.ResultCode;
import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.member.dto.request.LoginDTO;
import com.sungwon.api.member.dto.request.MemberDTO;
import com.sungwon.api.member.dto.request.MemberRoleDTO;
import com.sungwon.api.member.dto.request.SearchRequestMemberDTO;
import com.sungwon.api.member.entity.*;
import com.sungwon.api.member.repository.MemberRepository;
import com.sungwon.api.member.repository.MemberRoleRepository;
import com.sungwon.api.team.entity.QTeam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.sungwon.api.board.entity.QBoard.board;
import static com.sungwon.api.config.auth.JwtConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	@Value("${spring.data.db.string.merge}")
	private String db_string_merge;

	@Autowired
	private CommonUtil commonUtilService;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberRoleRepository memberRoleRepository;

	@Autowired
	private RoleService roleService;

	@PersistenceContext
	EntityManager em;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	SearchRepository searchRepository;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	JPAQueryFactory queryFactory;
	QMember m;
	QMemberRole mr;
	QTeam t;
	QRole r;
	
	public ResponseDTO findAll() {
		ResponseDTO responseDTO = new ResponseDTO();

		// [1번째 방식] JPA 형식으로 list 구현
//		List<Member> members = new ArrayList<>();
//		memberRepository.findByUseYn("Y").forEach(e -> members.add((Member) e));

		// [2번째 방식] queryDSL 이용한 list 구현
		List<Member> members = selectMember("Y");
		responseDTO = commonUtilService.selectObject(members);

		// [3번째 방식] Member 테이블에 Team 조인, MemberRole 서브쿼리한 queryDSL
//		responseDTO = selectMemberInfo("Y");

		// [4번째 방식] 검색조건(where) 사용한 queryDSL
//		SearchRequestMemberDTO requestDTO = new SearchRequestMemberDTO();
//		requestDTO.setUseYn("Y");
//		responseDTO = searchInfo(requestDTO);

		// [5번째 방식] MyBatis의 SQL 이용한 방식
//		ArrayList<HashMap<String, Object>> list = memberMapper.findAll();
//		if(list.size() > 0) {
//			responseDTO.setResultCode(ResultCode.SUCCESS.getName());
//			responseDTO.setMsg(ResultCode.SUCCESS.getValue());
//			responseDTO.setRes(list);
//		} else {
//			responseDTO.setResultCode(ResultCode.NOT_FOUND_INFO.getName());
//			responseDTO.setMsg(ResultCode.NOT_FOUND_INFO.getValue());
//		}

		return responseDTO;
	}

	public Header<List<MemberDTO>> getMemberList(Pageable pageable, SearchCondition searchCondition) {
		List<MemberDTO> list = new ArrayList<>();

//		Page<Member> members = memberRepository.findByUseYnOrderByMemberSeqDesc(pageable, "Y");
		Page<Member> members = searchRepository.findAllByMember(pageable, searchCondition);
		for (Member m : members) {
			MemberDTO dto = MemberDTO.builder()
					.memberSeq(m.getMemberSeq())
					.memberId(m.getMemberId())
					.name(m.getName())
					.teamId(m.getTeamId())
					.mobile(m.getMobile())
					.build();

			list.add(dto);
		}

		Pagination pagination = new Pagination(
                (int) members.getTotalElements()
                , pageable.getPageNumber() + 1
				, pageable.getPageSize()
				, 10
		);

		return Header.OK(list, pagination);
	}

	private ResponseDTO searchInfo(SearchRequestMemberDTO dto) {
		List<Tuple> result;
		m = QMember.member;
		mr = QMemberRole.memberRole;
		t = QTeam.team;
		queryFactory = new JPAQueryFactory(em);
		result = queryFactory
				.select(m.memberId
						, m.mobile
						, m.name
						, select(Expressions.stringTemplate("concat({0},'팀')", t.teamNm))
								.from(t)
								.where(t.teamId.eq(m.teamId))
						, select(Expressions.stringTemplate(db_string_merge+"({0}, '|')", mr.roleSeq.stringValue()))
								.from(mr)
								.where(mr.memberSeq.eq(m.memberSeq))
				)
				.from(m)
				.where(memberIdEq(dto.getMemberId())
						, teamIdEq(dto.getTeamId())
						, useYnEq(dto.getUseYn())
				).fetch().stream().toList();

		String[] column = {"memberId","mobile","name","teamNm","roles"};
		ResponseDTO data = commonUtil.convertJsonArray(column, result);

		return data;
	}

	public List<Member> selectMember(String useYn) {
		m = QMember.member;
		queryFactory = new JPAQueryFactory(em);
		JPAQuery<Member> query = queryFactory.selectFrom(m).where(searchRepository.searchMemberKeywords("useYn", "Y"));
		long total = query.fetch().stream().count();
		List<Member> list = query.fetch().stream().toList();

		return list;
	}

	public ResponseDTO selectMemberInfo(String useYn) {
		List<Tuple> result;
		m = QMember.member;
		mr = QMemberRole.memberRole;
		t = QTeam.team;
		queryFactory = new JPAQueryFactory(em);
		result = queryFactory
				.select(m.memberId
						, m.mobile
						, m.name
						, select(Expressions.stringTemplate("concat({0},'팀')", t.teamNm))
							.from(t)
							.where(t.teamId.eq(m.teamId))
						, select(Expressions.stringTemplate(db_string_merge+"({0}, '|')", mr.roleSeq.stringValue()))
							.from(mr)
							.where(mr.memberSeq.eq(m.memberSeq))
				)
				.from(m)
				.where(m.useYn.eq(useYn))
				.fetch().stream().toList();

		String[] column = {"memberId","mobile","name","teamNm","roles"};
		ResponseDTO data = commonUtil.convertJsonArray(column, result);

		return data;
	}

	public ResponseDTO getMemberDetail(long memberSeq){
		List<Tuple> info;
		m = QMember.member;
		t = QTeam.team;
		mr = QMemberRole.memberRole;
		r = QRole.role;
		queryFactory = new JPAQueryFactory(em);
		info = queryFactory.from(m)
				.select(m.memberId
						, t.teamId
						, t.teamNm
						, m.name
						, m.email
						, m.mobile
						, select(Expressions.stringTemplate(db_string_merge+"({0}, '|')", r.name))
							.from(mr)
							.innerJoin(r).on(r.roleSeq.eq(mr.roleSeq))
							.where(mr.memberSeq.eq(m.memberSeq))
				)
				.innerJoin(t).on(t.teamId.eq(m.teamId))
				.where(m.memberSeq.eq(memberSeq))
				.fetch().stream().toList();
		
		String[] column = {"memberId","teamId","teamNm","name","email","mobile","roles"};
		ResponseDTO data = commonUtil.convertJsonArray(column, info);
		return data;
	}

	public Optional<Member> findById(long memberSeq){
		Optional<Member> member;
		m = QMember.member;
		queryFactory = new JPAQueryFactory(em);
		member = Optional.ofNullable(
				queryFactory.selectFrom(m)
                .where(m.memberSeq.eq(memberSeq)).fetchOne());

//		Optional<Member> member = memberRepository.findById(memberSeq);
		return member;
	}

	public ResponseDTO login(LoginDTO dto) {
		Optional<Member> m = memberRepository.findByMemberId(dto.getMemberId());
		String password = m.get().getPassword();
		ResponseDTO responseDTO = new ResponseDTO();
		if (m.isEmpty()) {
			responseDTO.setResultCode(ResultCode.NOT_FOUND_MEMBER.getName());
			responseDTO.setMsg(ResultCode.NOT_FOUND_MEMBER.getValue());
		} else if (!password.equals(dto.getPassword())) {
			responseDTO.setResultCode(ResultCode.NOT_VALIDATED_PASSWORD.getName());
			responseDTO.setMsg(ResultCode.NOT_VALIDATED_PASSWORD.getValue());
		} else {
			// TODO : refreshToken 값 새로 생성하여 member 업데이트
			Map<String, String> tokens = createRefreshToken(m.get());
			Member member = new Member();
			BeanUtils.copyProperties(m.get(), member);
			member.setRefreshToken(tokens.get("accessToken").toString());
			memberRepository.save(member);

			responseDTO.setResultCode(ResultCode.SUCCESS.getName());
			responseDTO.setMsg(ResultCode.SUCCESS.getValue());
			responseDTO.setRes(member);
		}

		return responseDTO;
	}

	@Transactional("transactionManager")
	public ResponseDTO insert(MemberDTO dto) {
		Member member = new Member();
		BeanUtils.copyProperties(dto, member);
		ResponseDTO responseDTO = validationCheck(member, "I");
		if ("".equals(responseDTO.getResultCode()) || responseDTO.getResultCode() == null) {
			memberRepository.save(member);
			System.out.println("memberSeq = " + member.getMemberSeq());
			responseDTO.setResultCode(ResultCode.INSERT.getName());
			responseDTO.setMsg(ResultCode.INSERT.getValue());
			responseDTO.setRes(member);
		}

		return responseDTO;
	}

	@Transactional("transactionManager")
	public ResponseDTO update(Long memberSeq, MemberDTO dto) {
		Optional<Member> m = memberRepository.findByMemberSeq(memberSeq);
		ResponseDTO responseDTO = new ResponseDTO();
		if(m.isPresent()) {
			Member member = new Member();
			BeanUtils.copyProperties(dto, member);
			member.setMemberSeq(memberSeq);
			responseDTO = validationCheck(member, "U");
			if ("".equals(responseDTO.getResultCode()) || responseDTO.getResultCode() == null) {
				memberRepository.save(member);
				responseDTO.setResultCode(ResultCode.UPDATE.getName());
				responseDTO.setMsg(ResultCode.UPDATE.getValue());
				responseDTO.setRes(member);
			}
		} else {
			responseDTO.setResultCode(ResultCode.NOT_FOUND_MEMBER.getName());
			responseDTO.setMsg(ResultCode.NOT_FOUND_MEMBER.getValue());
		}
		return responseDTO;
	}

	@Transactional("transactionManager")
	public ResponseDTO delete(long memberSeq){
		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Member> m = memberRepository.findByMemberSeq(memberSeq);
		if(m.isPresent()){
			Member member = m.get();
			member.setUseYn("N");
			memberRepository.save(member);
			responseDTO.setResultCode(ResultCode.DELETE.getName());
			responseDTO.setMsg(ResultCode.DELETE.getValue());
			responseDTO.setRes(member);
		} else {
			responseDTO.setResultCode(ResultCode.NOT_FOUND_MEMBER.getName());
			responseDTO.setMsg(ResultCode.NOT_FOUND_MEMBER.getValue());
		}
		return responseDTO;
	}

	public ResponseDTO mappingById(Long memberSeq) {
		List<MemberRole> roles = memberRoleRepository.findByMemberSeqOrderByRoleSeq(memberSeq);
		ResponseDTO responseDTO = commonUtil.selectObject(roles);
		return responseDTO;
	}

	@Transactional("transactionManager")
	public ResponseDTO insertMemberRole(MemberRoleDTO dto) {
		ResponseDTO responseDTO = new ResponseDTO();
		List<Role> list = roleService.findAll();
		String[] role = dto.getRoles().split(",");
		for(int i=0 ; i<role.length; i++) {
			boolean roleCheck = false;
			for(int j=0; j<list.size(); j++) {
				if (!roleCheck) {
					roleCheck = (list.get(j).getRoleSeq() == Long.valueOf(role[i])) ? true : false;
				}
			}

			if (roleCheck) {
				MemberRole memberRole = new MemberRole();
				memberRole.setMemberSeq(dto.getMemberSeq());
				memberRole.setRoleSeq(Long.valueOf(role[i]));
				memberRoleRepository.save(memberRole);
				responseDTO.setResultCode(ResultCode.SUCCESS.getName());
				responseDTO.setMsg(ResultCode.SUCCESS.getValue());
				responseDTO.setRes(memberRole);
			} else {
				responseDTO.setResultCode(ResultCode.NOT_INSERT_MEMBER_ROLE_CHECK.getName());
				responseDTO.setMsg(ResultCode.NOT_INSERT_MEMBER_ROLE_CHECK.getValue());
				break;
			}
		}

		return responseDTO;
	}

	@Transactional("transactionManager")
	public ResponseDTO updateMemberRole(MemberRoleDTO dto) {
		ResponseDTO responseDTO = new ResponseDTO();
		List<MemberRole> roles = memberRoleRepository.findByMemberSeqOrderByRoleSeq(dto.getMemberSeq());
		String[] role = dto.getRoles().split(",");
		List<Role> list = roleService.findAll();
		for(int i=0 ; i<role.length; i++) {
			boolean roleCheck = false;
			boolean existedRole = false;
			for(int j=0; j<list.size(); j++) {
				if (!roleCheck) {
					roleCheck = (list.get(j).getRoleSeq() == Long.valueOf(role[i])) ? true : false;
				}
			}

			if (!roleCheck) {
				responseDTO.setResultCode(ResultCode.NOT_INSERT_MEMBER_ROLE_CHECK.getName());
				responseDTO.setMsg(ResultCode.NOT_INSERT_MEMBER_ROLE_CHECK.getValue());
				break;
			}

			for (int j=0; j<roles.size(); j++) {
				Long insRole = Long.valueOf(role[i]);
				if(!existedRole) {
					existedRole = (insRole == roles.get(j).getRoleSeq()) ? true : false;
				}
			}

			if(!existedRole && roleCheck) {
				MemberRole memberRole = new MemberRole();
				memberRole.setMemberSeq(dto.getMemberSeq());
				memberRole.setRoleSeq(Long.valueOf(role[i]));
				memberRoleRepository.save(memberRole);
			}

			responseDTO.setResultCode(ResultCode.SUCCESS.getName());
			responseDTO.setMsg(ResultCode.SUCCESS.getValue());
		}

		return responseDTO;
	}

	@Transactional("transactionManager")
	public ResponseDTO deleteMemberRole(MemberRoleDTO dto) {
		ResponseDTO responseDTO = new ResponseDTO();
		String[] role = dto.getRoles().split(",");
		int delCnt = 0;
		for(int i=0 ; i<role.length; i++) {
			Optional<MemberRole> mr = memberRoleRepository.findByMemberSeqAndRoleSeq(dto.getMemberSeq(), Long.valueOf(role[i]));
			if(!mr.isEmpty()) {
				MemberRole memberRole = new MemberRole();
				memberRole.setMemberSeq(dto.getMemberSeq());
				memberRole.setRoleSeq(Long.valueOf(role[i]));
				memberRoleRepository.deleteById(mr.get().getMemberRoleSeq());
				delCnt++;
			}
		}

		if (delCnt > 0) {
			responseDTO.setResultCode(ResultCode.DELETE.getName());
			responseDTO.setMsg(ResultCode.DELETE.getValue());
		} else {
			responseDTO.setResultCode(ResultCode.NOT_DELETE_MEMBER_ROLE.getName());
			responseDTO.setMsg(ResultCode.NOT_DELETE_MEMBER_ROLE.getValue());
		}

		return responseDTO;
	}

	public Map<String, String> createRefreshToken(Member member) {
		long now = System.currentTimeMillis();
		Map<String, String> accessTokenResponseMap = new HashMap<>();
		String accessToken = JWT.create()
				.withSubject(member.getEmail())
				.withExpiresAt(new Date(now + AT_EXP_TIME))
				.sign(Algorithm.HMAC256(ADMIN_JWT_SECRET));

		accessTokenResponseMap.put(AT_HEADER, accessToken);
		return accessTokenResponseMap;
	}

	public ResponseDTO validationCheck(Member member, String type) {
		ResponseDTO responseDTO = new ResponseDTO();
		List<Member> list = memberRepository.findByMemberId(member.getMemberId()).stream().toList();
		if ("I".equals(type) && list.size() > 0) {
			responseDTO.setResultCode(ResultCode.NOT_INSERT_SAME_MEMBER_ID.getName());
			responseDTO.setMsg(ResultCode.NOT_INSERT_SAME_MEMBER_ID.getValue());
		} else if (member.getEmail() != null && !RegexUtils.isValidEmail(member.getEmail())) {
			responseDTO.setResultCode(ResultCode.NOT_VALIDATED_EMAIL.getName());
			responseDTO.setMsg(ResultCode.NOT_VALIDATED_EMAIL.getValue());
		} else if (member.getMobile() != null && !RegexUtils.isValidPhoneNumber(member.getMobile())) {
			responseDTO.setResultCode(ResultCode.NOT_VALIDATED_MOBILE.getName());
			responseDTO.setMsg(ResultCode.NOT_VALIDATED_MOBILE.getValue());
		}

		return responseDTO;
	}

	private Predicate memberIdEq(String memberId) {
		return memberId == null ? null : m.memberId.eq(memberId);
	}
	private Predicate teamIdEq(String teamId) {
		return teamId == null ? null : m.teamId.eq(teamId);
	}
	private Predicate useYnEq(String useYn) {
		return useYn == null ? null : m.useYn.eq(useYn);
	}

}
