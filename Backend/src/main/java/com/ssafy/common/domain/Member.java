package com.ssafy.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert
public class Member {
	// 회원넘버
	@Id
	@Column(name="")
	@GeneratedValue
	private Long no;

	// 회원 이메일
	@Column(name="member_email",length = 30, unique = true, nullable = false)
	private String email;

	// 회원 비밀번호
	@Column(name="member_password",length = 100, nullable = false)
	private String password;

	// 회원 이름
	@Column(name="member_name",length = 20, unique = true, nullable = false)
	private String name;

	// 회원 방식
	@Column(name="member_way",length = 4)
	@ColumnDefault(value = "'M00'")
	@Enumerated(EnumType.STRING)
	private MemberWay way;

	// 선호하는 문제 사이트
	@OneToMany(mappedBy = "memberNo", cascade = CascadeType.ALL)
	private List<Problem_Site_Like> problemSiteList = new ArrayList<>();

	// 선호하는 언어
	@OneToMany(mappedBy = "memberNo", cascade = CascadeType.ALL)
	private List<Use_Language_Like> useLanguageLike = new ArrayList<>();

	@ColumnDefault(value = "'ROLE_USER'")
	@Enumerated(EnumType.STRING)
	private Member_Authority authority;

//	@Builder
//	public Member(String email, String password, Member_Authority authority) {
//		this.member_email = email;
//		this.member_password = password;
//		this.member_authority = authority;
//	}

	// 생성 메서드
	public void createMember(List<Problem_Site_Like> problem_site_like, List<Use_Language_Like> use_language_like) {
		// 선호하는 문제사이트 집어넣음
		for (Problem_Site_Like pslike : problem_site_like) {
			this.problemSiteList.add(pslike);
		}

		// 선호하는 언어 집어넣음
		for (Use_Language_Like ullike : use_language_like) {
			this.useLanguageLike.add(ullike);
		}

	}
}
