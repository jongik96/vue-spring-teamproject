package com.ssafy.common.domain.member;

import java.io.Serializable;

import lombok.Data;

@Data
public class Member_FollowPK implements Serializable{
  private long follow_no;
  private long member_no;
}
