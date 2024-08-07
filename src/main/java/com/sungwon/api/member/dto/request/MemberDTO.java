package com.sungwon.api.member.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO implements Serializable {

	private Long memberSeq;

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
