package com.ssafy.common.domain.discuss;
import java.io.Serializable;
import lombok.Data;
@Data
public class Discuss_Recomment_LikePK implements Serializable{
  private long discussRecommentNo;
  private long member; 
}
