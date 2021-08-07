package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.domain.Notification_SubTask;
import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.HelpmeDTO;
import com.ssafy.common.service.HelpmeService;
import com.ssafy.common.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/helpme")
@RequiredArgsConstructor
public class HelpmeContoller {

	private final HelpmeService helpmeSevice;
	private final NotificationService notificationService;
	
	// memberNo가 받은 문제풀이 요청 목록
	@GetMapping("/receptlist/{memberNo}")
	public Map<String,List<HelpmeDTO>> getReceptHelpmeListByMemberNo(@PathVariable Long memberNo, @RequestParam(defaultValue = "0") int page) {
		Map<String, List<HelpmeDTO>> ret=new HashMap<>();
		List<HelpmeDTO> list =helpmeSevice.getReceptHelpmeListByMemberNo(memberNo, page);
		
		ret.put("helpmeReceptList", list);
		
		return ret;
	}
	
	
	// memberNo가 신청한 문제풀이 요청 목록(로그인 유저와 memberNo가 일치해야만 보여줌)
	@GetMapping("/sendlist")
	public Map<String,List<HelpmeDTO>> getSendHelpmeListByMemberNo(@RequestParam(defaultValue = "0") int page) {
		Map<String, List<HelpmeDTO>> ret=new HashMap<>();
		List<HelpmeDTO> list =helpmeSevice.getSendHelpmeListByMemberNo(page);
		
		ret.put("helpmeSendList", list);
		
		return ret;
	}
	
	// 문제풀이 요청 게시글 작성
	@PostMapping()
	public Map<String, Object>  createHelpme(@RequestBody Map<String, String> req) {
		Map<String, Object> ret=new HashMap<>();
		Helpme helpme =new Helpme();
		
		//요청 받은사람 No
		Member member=new Member();
		member.setNo(Long.parseLong(req.get("receptMemberNo")));
		helpme.setHelpmeReceptorNo(member);
		
		//요청 문제
		Problem_Site_List problemSiteName= new Problem_Site_List();
		problemSiteName.setProblemSiteName(req.get("problemSiteName"));
		Problem_Site problem_Site=new Problem_Site();
		problem_Site.setProblemNo(Long.parseLong(req.get("problemNo")));
		problem_Site.setProblemSiteName(problemSiteName);
		helpme.setProblemSite(problem_Site);
		
		//요청 내용
		helpme.setHelpmeContent(req.get("content"));
		
		Long helpmeNo =null;
		try {
			helpmeNo=helpmeSevice.createHelpme(helpme);			
		}catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		
		//요청 받은사람에게 알림전송
		notificationService.helpmeChangeState(helpmeNo,member.getNo() , Notification_SubTask.NHS);
		
		ret.put("helpme", helpmeNo);
		ret.put("success", "True");
		
		
		return ret;
	}
	
	//게시글 상세보기
	@GetMapping("/{helpmeNo}")
	public Map<String, HelpmeDTO> getHelpme(@PathVariable Long helpmeNo) {
		Map<String, HelpmeDTO> ret=new HashMap<>();
		HelpmeDTO helpme;
		
		helpme=helpmeSevice.getHelpme(helpmeNo);
		
		ret.put("helpme", helpme);		
		
		return ret;		
	}
	
	
	//게시글 수정
	@PutMapping("/{helpmeNo}")
	public Map<String, Object> updateHelpme(@PathVariable Long helpmeNo, @RequestBody Map<String, String> req) {
		Map<String, Object> ret=new HashMap<>();
		Helpme helpme =new Helpme();
		
		
		//요청 받은사람 No
		Member member=new Member();
		member.setNo(Long.parseLong(req.get("receptMemberNo")));
		helpme.setHelpmeReceptorNo(member);
		
		//요청 문제
		Problem_Site_List problemSiteName= new Problem_Site_List();
		problemSiteName.setProblemSiteName(req.get("problemSiteName"));
		Problem_Site problem_Site=new Problem_Site();
		problem_Site.setProblemNo(Long.parseLong(req.get("problemNo")));
		problem_Site.setProblemSiteName(problemSiteName);
		helpme.setProblemSite(problem_Site);
		
		//요청 내용
		helpme.setHelpmeContent(req.get("content"));
		
		Long helpmeNO=null;
		try {
			helpmeNO=helpmeSevice.updateHelpme(helpmeNo, helpme);		
		}catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		
		ret.put("success","True");
		ret.put("helpme",helpmeNO);		
		
		return ret;
	}
	
	//게시글 삭제
	@DeleteMapping("/{helpmeNo}")
	public Map<String, Object> deleteHelpme(@PathVariable Long helpmeNo) {
		Map<String, Object> ret=new HashMap<>();
		
		try {
			helpmeSevice.deleteHelpme(helpmeNo);
		}catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		ret.put("success","True");
		
		return ret;
	}
	
	//풀이요청 응답
	@PostMapping("/state/{helpmeNo}")
	public Map<String, Object> changeState(@PathVariable Long helpmeNo, @RequestBody Map<String, String> req) {
		Map<String, Object> ret=new HashMap<>();
		String answer=req.get("answer");
		long helpmeSenderNo=0L;
		try {
			helpmeSenderNo=helpmeSevice.changeState(helpmeNo,answer);
			if(answer.equals("accept")) {
				ret.put("msg", "수락");
				notificationService.helpmeChangeState(helpmeNo,helpmeSenderNo , Notification_SubTask.NHA);
			}else if(answer.equals("reject")){
				ret.put("msg", "거절");
				notificationService.helpmeChangeState(helpmeNo,helpmeSenderNo , Notification_SubTask.NHR);
			}	
		}catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		
		ret.put("success", "True");

		return ret;
	}
	
	//풀이요청 답변
	//helpme게시판에 해당 게시글 답변게시글로 등록하고 답변완료 알림 전송
	@PostMapping("/answer/{helpmeNo}")
	public Map<String, Object> explainArticleMapping(@PathVariable Long helpmeNo,@RequestBody Map<String,Long> req) {
		Map<String, Object> ret=new HashMap<>();
		Long answerArcleNo=req.get("answerArticleNo");
		long helpmeSenderNo=0L;
		try {
			//문제풀이게시글을 풀이요청게시글의 답변으로 등록
			helpmeSevice.answerArticleMapping(answerArcleNo, helpmeNo);
			//풀이요청 게시글의 상태를 답변완료로 변경
			helpmeSenderNo=helpmeSevice.changeState(helpmeNo,"end");
			//풀이요청 게시글의 작성자에게 답변이 완료되었다고 알림전송
			notificationService.helpmeChangeState(helpmeNo,helpmeSenderNo , Notification_SubTask.NHE);
		}catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		
		ret.put("success", "True");
		return ret;
	}
	
}
