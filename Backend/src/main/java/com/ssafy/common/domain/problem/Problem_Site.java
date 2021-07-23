package com.ssafy.common.domain.problem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
@IdClass(Problem_SitePK.class)
public class Problem_Site {
  @Id
  @Column(length = 30,name = "PROBLEM_SITE_NAME") //헌국 문제싸이트 리스트 테이블 받으면 연관관계 설정 
  private String problemSiteName;

  @Id
  @Column(name="PROBLEM_NO")
  private long problemNo;

  @Column(length = 500,name="PROBLEM_SITE_LINK")
  private String problemSiteLink;
}
