package com.sungwon.api.member.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MemberDTO {

	@Column(length = 20)
	private String memberId;

	@Column(length = 20)
	private String teamId;

	@Column(length = 20)
	private String name;

	private String password;

	@Column(length = 50)
	private String email;

	@Column(length = 20)
	private String mobile;

	private String roles;

//	private List<Role> roles;

}
