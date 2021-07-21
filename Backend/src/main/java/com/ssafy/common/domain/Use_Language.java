package com.ssafy.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Use_Language { //헌국씨꺼
  @Id
  @Column(length = 30)
  private String language; 
}
