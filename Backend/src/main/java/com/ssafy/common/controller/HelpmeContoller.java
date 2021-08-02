package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.HelpmeDTO;
import com.ssafy.common.service.HelpmeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/helpme")
@RequiredArgsConstructor
public class HelpmeContoller {

	private final HelpmeService helpmeSevice;
	
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
		
		Long helpmeNO =null;
		try {
			helpmeNO=helpmeSevice.createHelpme(helpme);			
		}catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		
		ret.put("helpmeNo", helpmeNO);
		ret.put("success", "True");
		
		
		return ret;
	}
	
	
}
