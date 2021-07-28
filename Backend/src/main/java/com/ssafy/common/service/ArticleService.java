package com.ssafy.common.service;

import java.util.Map;


public interface ArticleService {
  public Map<String, Object> sltMultiArticle(String articleClass);
  public Map<String, Object> sltOneArticle(String articleClass, long pk);
  public Map<String, Object> deleteArticle(String articleClass, long pk);
  public Map<String, Object> updateArticle(String articleClass, long articlePk, Map<String, Object> req);
  public Map<String, Object> insertArticle(String articleClass, Map<String, Object> req);
}
