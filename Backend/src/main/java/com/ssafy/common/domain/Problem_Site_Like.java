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
@IdClass(Problem_Site_Like_Id.class)
public class Problem_Site_Like {

	@Id
	@ManyToOne
	@JoinColumn(name="problem_site_name")
	private Problem_Site_List problem_site_list;
	
	@Id
	@JoinColumn(name="member_no")
	private Member member;
}

