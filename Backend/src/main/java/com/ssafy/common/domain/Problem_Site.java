package com.ssafy.common.domain;

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
  @Column(length = 30)
  private String problem_site_name;

  @Id
  @Column
  private int problem_no;

  @Column(length = 500)
  private String problem_site_link;
}
