package com.ssafy.common.repository.discuss;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.article.Article_Comment;

public interface Discuss_CommentRepository extends JpaRepository<Article_Comment,Long>,Discuss_CommentRepositoryCustom{


}
