package com.ssafy.common.repository;
import java.util.List;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.article.Article;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.ssafy.common.domain.article.QArticle.*;
// import 




@Repository
public class ArticleRepositorySupport extends QuerydslRepositorySupport{
  private final JPAQueryFactory queryFactory;
  public ArticleRepositorySupport(JPAQueryFactory queryFactory){
      super(Article.class);
      this.queryFactory = queryFactory;
    }

    public List<Article>findAll(){
      List<Article> aa  = queryFactory.selectFrom(article).fetch();
      return aa;
    }
  }

  // @PersistenceContext
  // private EntityManager em;

  // public List<Article> SltMul(String articleClass,int page){
  //   Article findAll()
  //   // List<Article> articleList = new ArrayList<Article>();
  //   // CriteriaBuilder cb = em.getCriteriaBuilder();
  //   // CriteriaQuery<Article> query = cb.createQuery(Article.class);
  //   // Root<Article> a =query.from(Article.class);
  //   // CriteriaQuery<Article> articles = query.select(a); 
  // }
  
