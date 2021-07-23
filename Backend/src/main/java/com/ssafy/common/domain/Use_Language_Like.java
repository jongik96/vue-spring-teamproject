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
@IdClass(Use_Language_LikePK.class)
public class Use_Language_Like {

	@Id
	@ManyToOne
	@JoinColumn(name = "use_language")
	private Use_Language useLanguage;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "member_no")
	private Member memberNo;	
	
}
