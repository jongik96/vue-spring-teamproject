package com.ssafy.common.domain.helpme;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.common.domain.member.Member;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Helpme_Comment_LikePK.class)
public class Helpme_Comment_Like {
  @Id
  @ManyToOne
  @JoinColumn(name="HELPME_COMMENT_NO")
  private Helpme_Comment helpmeCommentNo;

  @Id
  @ManyToOne
  @JoinColumn(name = "MEMBER_NO")
  private Member member;
}
