package com.ssafy.common.domain;
import java.io.Serializable;

import lombok.Data;

@Data
public class Article_AlgorithmPK implements Serializable{
  // private static final long serialVersionUID = -3033073092220146757L;
  private String algorithm_name;
  private long article_no;

}
