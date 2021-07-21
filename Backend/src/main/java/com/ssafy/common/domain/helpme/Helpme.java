package com.ssafy.common.domain.helpme;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.Member;
import com.ssafy.common.domain.Problem_Site;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Helpme {
  @Id
  @GeneratedValue
  @Column(name="HELPME_NO")
  private long helpme_no;

  @ManyToOne
  @JoinColumns({
                @JoinColumn(name="PROBLEM__NO_HELPME"),
                @JoinColumn(name="PROBLEM_SITE_NAME_HELPME")
  })
  private Problem_Site problem_site;
  
  @ManyToOne
  @JoinColumn(name = "HELP_MEMBER_NO")
  private Member writer;

  @ManyToOne
  @JoinColumn(name = "RECEPT_MEMBER_NO")
  private Member receptor;

  @Column(length = 500,name="HELPME_CONTENT")
  private String content;

  @Column(length = 4, name = "HELPME_STATUS")
  @Enumerated(EnumType.STRING)
  private Helpme_Class helpme_class;

  @Column(name = "HELPME_DATE")
  private Timestamp helpmeDate;
}
