package com.ssafy.common.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;


import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.Use_Language;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;
import com.ssafy.common.domain.article.Article_Class;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.Article_Like;
import com.ssafy.common.domain.discuss.Discuss;
import com.ssafy.common.domain.discuss.Discuss_Host;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.Article_CommentDTO;
import com.ssafy.common.dto.DiscussDTO;
import com.ssafy.common.dto.Discuss_CommentDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.Use_LanguageRepository;
import com.ssafy.common.repository.Algorithm.AlgorithmRepository;
import com.ssafy.common.repository.article.ArticleRepository;
import com.ssafy.common.repository.article.Article_AlgorithmRepository;
import com.ssafy.common.repository.article.Article_CommentRepository;
import com.ssafy.common.repository.article.Article_LikeRepository;
import com.ssafy.common.repository.discuss.DiscussRepository;
import com.ssafy.common.repository.discuss.Discuss_CommentRepository;
import com.ssafy.common.repository.discuss.Discuss_HostRepository;
import com.ssafy.common.repository.member.MemberRepository;
import com.ssafy.common.repository.problem.Problem_Site_ListRepository;
import com.ssafy.common.repository.problem.Problem_Site_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
  @Autowired
  private  ArticleRepository ArticleRepo;
  @Autowired
  private Article_CommentRepository articleCommetRepo;
  
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private Problem_Site_Repository problemSiteRepo;
  @Autowired
  private Use_LanguageRepository useLanguageRepo;
  @Autowired
  private AlgorithmRepository AlgoRepo;
  @Autowired
  private Article_AlgorithmRepository ArtiAlgoRepo;
  @Autowired
  private Problem_Site_ListRepository problemSiteListRepo;
  @Autowired
  private Article_LikeRepository ArticleLikeRepo;
  
  @Autowired
  private DiscussRepository disRepo;
  @Autowired
  private Discuss_CommentRepository disComRepo;
  @Autowired
  private Discuss_HostRepository disHostRepo;
  @Override
  public Map<String, Object> sltMultiArticle(String articleClass,int page) {
	  
	//게시글 다건조회.. 추후 페이징처리 예정
	 
    Map<String, Object> res = new HashMap<String,Object>();
    Long nowLoginMemberNo=0L;
	try {
		//로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
		nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
	}catch (RuntimeException e) {
		nowLoginMemberNo=0L;
	}
    if (articleClass.equals("main")){
    	
    }else if(articleClass.equals("article")){
    	//게시글 전체 조회 결과
        List<ArticleDTO> articleList = ArticleRepo.sltMulti(nowLoginMemberNo, PageRequest.of(page, 20)).orElse(null);
        res.put("article", articleList);
        if (articleList.size()==0) {//게시글이 존재하지 않을 시.
        	res.put("msg", "일반 게시글이 존재하지 않습니다.");
        }
    }else if(articleClass.equals("qna")){
        List<ArticleDTO> articleList = ArticleRepo.sltMultiQnA(nowLoginMemberNo, PageRequest.of(page, 20), Article_Class.A00).orElse(null);
        res.put("article", articleList);
        if (articleList.size()==0) //게시글이 존재하지 않을 시.
        	res.put("msg", "QnA 게시글이 존재하지 않습니다.");

    }
    else if(articleClass.equals("discussion")){
    	List<DiscussDTO>discussList = disRepo.sltMulti(PageRequest.of(page, 20)).orElse(null);
    	res.put("discussion",discussList);
    	 if (discussList.size()==0) //게시글이 존재하지 않을 시.
         	res.put("msg", "토론이 존재하지 않습니다.");
    }
    return res;
    
  }
  
  
  
  @Override
  public Map<String, Object>sltOneArticle(String articleClass,long pk,int page){
	  //게시글 단건조회 및 댓글 조회
	Long nowLoginMemberNo=0L;
	try {
		//로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
		nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
	}catch (RuntimeException e) {
		nowLoginMemberNo=0L;
	}
	String msg = "";
	  Map<String,Object>res = new HashMap<String,Object>();
	  	if(articleClass.equals("article")){
	    	ArticleDTO article = ArticleRepo.sltOne(pk, nowLoginMemberNo);
	    	if (article != null) {
		    	List<Article_CommentDTO> comments = articleCommetRepo.artiComments(pk,nowLoginMemberNo,PageRequest.of(page, 20)).orElse(null);
		    	if (comments.size() == 0) {
		    		msg = "등록된 댓글이 없습니다.";
		    	}
		    	res.put("articleDetail",article);
		    	res.put("articleComments", comments);
	    	}else {
	    		msg="게시글이 존재하지 않습니다.";
	    	}
	    }
	    else if(articleClass.equals("discussion")){
	    	DiscussDTO article = disRepo.sltOne(pk, nowLoginMemberNo);
	    	if (article != null) {
	    		List<Discuss_CommentDTO> comments = disComRepo.artiComments(pk,nowLoginMemberNo,PageRequest.of(page, 20)).orElse(null);
		    	if (comments.size() == 0) {
		    		msg = "등록된 댓글이 없습니다.";
		    	}
	    	}
	    	else {
	    		msg="해당 토론이 존재하지 않습니다.";
	    	}
	    	
	    }
	  	res.put("msg",msg);
	  return res;
  }
  @Override
  public Map<String, Object>deleteArticle(String articleClass,long articlePk){
	  Map<String,Object>res = new HashMap<String,Object>();
	  String msg = "";
	  Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	  	if(articleClass.equals("article")){
	  		
	  		//삭제 전 단건조회
	    	Article article = ArticleRepo.sltOneArticle(articlePk);
	    	if (article == null) {
	    		msg = "존재하지 않는 게시글입니다.";
	    	}else {
	    		//삭제 전 유저 일치 조회
	    		if(member != article.getMember()){
	    		msg ="자신의 글만 삭제할 수 있습니다.";
	    		}else{
		    		long deletedArticle = ArticleRepo.articleDelete(articlePk);
		    		res.put("deletedArtcle", deletedArticle);
		    		msg = "성공적으로 삭제되었습니다.";
	    		}
	    	}

	    	res.put("msg",msg);

	    }
	    else if(articleClass.equals("discussion")){
	    	
	    }
	  return res;
  }
@Override
public Map<String, Object> updateArticle(String articleClass, long articlePk, Map<String, Object> req) {
	Map<String,Object>res = new HashMap<String,Object>();
	String msg = "";
	Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	if(articleClass.equals("article")){
		//수정 전 단건 조회
		Article article = ArticleRepo.sltOneArticle(articlePk);
		if (article == null) {
    		msg = "존재하지 않는 게시글입니다.";
    	}else {
    		if(member != article.getMember()){
	    		msg ="자신의 글만 삭제할 수 있습니다.";
	    		}else{
	        		String content = (String) req.get("content");
	        		long updatedArticle = ArticleRepo.updateArticle(articlePk,content);
	        		res.put("updatedArtcle", updatedArticle);
	        		msg = "성공적으로 수정되었습니다.";
	    		}
    	}

    	res.put("msg",msg);
	}else if(articleClass.equals("discussion")){
    	
    }
	return res;
}
@Override
public Map<String, Object> insertArticle(String articleClass,Map<String, Object> req){
	String msg = "";
	Map<String,Object>res = new HashMap<String,Object>();
	Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	//임시멤버 등록
//	long memberNo = 1;
//	Member member = memberRepository.findByNo(memberNo).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	if (articleClass.equals("article")){
		String category = (String) req.get("category");
		String language = (String) req.get("language");
		String problemSiteNM = (String) req.get("pSite");
		String problemNoCheck =  (String) req.get("pNum");
		if (problemNoCheck.equals("")){
			msg = "문제넘버를 입력해주세요";
//			throw new IllegalStateException(msg);
		}else {
			Problem_Site_List problemSite = problemSiteListRepo.findOne(problemSiteNM);
			if (problemSite == null){
				msg = "존재하지 않는 문제 사이트입니다.";
			}else {
				long problemNo = Long.valueOf(problemNoCheck);
				
				Problem_Site problem =problemSiteRepo.sltOneProblem(problemSite, problemNo);
				Use_Language useLanguage= useLanguageRepo.findOne(language);
				if (problem == null) {
					Problem_Site insertProblemSite = new Problem_Site();
					insertProblemSite.setProblemNo(problemNo);
					insertProblemSite.setProblemSiteName(problemSite);
					insertProblemSite.setProblemSiteLink("");
					problem =problemSiteRepo.save(insertProblemSite);
					problemSiteRepo.flush();
				}
				
				if(useLanguage == null){
					msg = "해당 언어가 존재하지 않습니다.";
				}else{
					Article insertArticle = new Article();
					insertArticle.setArticleClass(Article_Class.valueOf(category));
					insertArticle.setArticleContent((String) req.get("content"));
					insertArticle.setArticleTitle((String)req.get("title"));
					insertArticle.setMember(member);
					insertArticle.setProblemSite(problem);
					insertArticle.setUseLanguage(useLanguage);
					Article tempInserted = ArticleRepo.save(insertArticle);
					ArticleRepo.flush();
					if (category.equals("A01")){
						Article_Algorithm artiAlgo = new Article_Algorithm();
						List<String> usedAlgo = (List<String>) req.get("algo");
						if (usedAlgo !=null) {
						for (String algo : usedAlgo) {
							Algorithm sltOneAlgo = AlgoRepo.sltOne(algo);
							if (sltOneAlgo != null){
								artiAlgo.setAlgorithmName(sltOneAlgo);
								artiAlgo.setArticleNo(tempInserted);
								Article_Algorithm insertedArtiAlgo = ArtiAlgoRepo.save(artiAlgo);
							}else {
								msg = "알고리즘이 존재하지 않습니다.";
							}
						}
						}
						ArtiAlgoRepo.flush();
						
					}
					ArticleDTO inserted = ArticleRepo.sltOne(tempInserted.getArticleNo(), member.getNo());
					List<Article_CommentDTO> comments = articleCommetRepo.artiComments(inserted.getArticleNo(),member.getNo(),PageRequest.of(0, 20)).orElse(null);
					res.put("article", inserted);
					res.put("articleComments", comments);
				}
			}	
		}
	//초기 데이터 입력용 (관리자기능)
	}else if(articleClass.equals("discussion")){
		String[]hosts ={"KAKAO", "LINE", "PROGRAMMERS", "BAEKJOON"};
		String[]year = {"2016","2017","2018","2019","2020","2021"};
		String[]type = {"INTERN","BLIND RECRUITMENT","TEST","CODE"};
		String[]date = {"0103","0401","0602","1123","1105"};
		String a = "";
		Random r = new Random();
		
		for (int i =0; i<hosts.length;i++) {
			Long g = (long) i;
			String hostOne = hosts[i];
			Discuss_Host dh = new Discuss_Host();
			dh.setDiscussCompHostName(hostOne);
			dh.setDiscussCompHostNo(0L);
			Discuss_Host inserted = disHostRepo.save(dh);
			a = hostOne;
			for (String yearOne:year) {
				for (String typeOne:type) {
					int dateOne = r.nextInt(5);
					LocalDateTime d = LocalDateTime.parse(date[dateOne],DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					Discuss ds = new Discuss();
					ds.setDiscussCompHostNo(inserted);
					ds.setDiscussCompName(hostOne+" "+ yearOne+" "+typeOne);
					ds.setDiscussDate(d);
					for (int ii = 1; ii < 6; ii ++) {
						ds.setDiscussCompProblem(ii + "번");
					}
					Discuss insertedDiscuss = disRepo.save(ds);
				
				}
				
			}
			
		}
		
		
	}
	res.put("msg", msg);
	return res;
			
}
@Override
public Map<String, Object> likeArticle(String articleClass, long articlePk, Map<String, Object> req) {
	//로그인한 유저 존재하는지 조회
	Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	Map<String,Object>res = new HashMap<String,Object>();
	if (articleClass.equals("article")){
		//게시글 존재하는지 조회
		Article article = ArticleRepo.sltOneArticle(articlePk);
		//좋아요 누른적 있는지 조회
		Article_Like arli = ArticleLikeRepo.ifMemberExist(member);
		//눌려져있으면 delete. 안눌러져 있으면 insert
		if(arli == null) {
			Article_Like insertLike = new Article_Like();
			insertLike.setArticleNo(article);
			insertLike.setMember(member);
			ArticleLikeRepo.save(insertLike);
			res.put("mylike", true);
		}else {
			ArticleLikeRepo.deleteLike(member,article);
			res.put("mylike", false);
		}
		long likeCount = ArticleRepo.likeArticle(article);
		res.put("likeCount", likeCount);
		res.put("articleNo", article.getArticleNo());
	}else if(articleClass.equals("discussion")){
		
	}
	return res;
}
@Override
public Map<String,Object>sltMultiDiscussByHost(Long HostPK,int page){
	Map<String,Object>res = new HashMap<String,Object>();
	String msg = "";
	List<DiscussDTO> discussList = disRepo.sltMultiByDisHost(HostPK, PageRequest.of(page, 20)).orElse(null);
	res.put("article", discussList);
	if (discussList.size() ==0) {
		msg = "등록된 토론이 없습니다";
	}
	res.put("msg", msg);
	return res;
	
}

}
