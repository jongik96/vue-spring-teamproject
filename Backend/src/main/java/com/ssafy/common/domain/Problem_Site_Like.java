package com.ssafy.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;



@Entity
@Getter @Setter
@IdClass(Problem_Site_LikePK.class)
public class Problem_Site_Like {

	//Problem_Site_List 도메인과 관계 
	@Id
	@ManyToOne
	@JoinColumn(name="problem_site_name")
	private Problem_Site_List problem_site_name;
	
	//Member 도메인과 관계
	@Id
	@ManyToOne
	@JoinColumn(name="member_no")
	private Member member_no;
}

