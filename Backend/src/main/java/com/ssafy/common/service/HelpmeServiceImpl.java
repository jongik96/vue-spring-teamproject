package com.ssafy.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.domain.helpme.Helpme_Class;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_SitePK;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.HelpmeDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.HelpmeRepository;
import com.ssafy.common.repository.member.MemberRepository;
import com.ssafy.common.repository.problem.Problem_Site_ListRepository;
import com.ssafy.common.repository.problem.Problem_Site_Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HelpmeServiceImpl implements HelpmeService {

	private final HelpmeRepository helpmeRepository;
	private final MemberRepository memberRepository;
	private final Problem_Site_Repository problem_Site_Repository;
	private final Problem_Site_ListRepository problem_Site_ListRepository;

	// memberNo가 받은 문제풀이 요청 목록
	@Override
	public List<HelpmeDTO> getReceptHelpmeListByMemberNo(Long memberNo,
			int page) {
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		List<HelpmeDTO> ret = helpmeRepository
				.getReceptHelpmeListByMemberNo(memberNo, nowLoginMemberNo,
						PageRequest.of(page, 20))
				.orElse(null);

		return ret;
	}

	// memberNo가 신청한 문제풀이 요청 목록
	// 이건 자기 프로필에서만 보여지는거니까 memberNo, nowLoginMemberNo가 동일인물임
	@Override
	public List<HelpmeDTO> getSendHelpmeListByMemberNo(int page) {
		Long memberNo = SecurityUtil.getCurrentMemberId();

		List<HelpmeDTO> ret = helpmeRepository
				.getSendHelpmeListByMemberNo(memberNo, PageRequest.of(page, 20))
				.orElse(null);

		return ret;
	}

	// 게시글 작성 (문제, 요청 받는사람No, 내용 넘어옴)
	@Override
	public Long createHelpme(Helpme helpme) {
		Member sendMember = memberRepository
				.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));

		// 게시글 작성자 설정
		helpme.setHelpmeSenderNo(sendMember);

		//본인에게 요청할 경우 못하게 해야함
		if(helpme.getHelpmeReceptorNo().getNo() == sendMember.getNo()) {
			throw new IllegalStateException("자신에게 요청 할 수 없습니다");
		}
		
		//입력받은 문제 받아옴
		Problem_Site problem_Site = helpme.getProblemSite();

		// 존재하는 사이트인지 확인
		Problem_Site_List problem_Site_List = problem_Site_ListRepository.findOne(problem_Site.getProblemSiteName().getProblemSiteName());
		if (problem_Site_List == null) {
			throw new IllegalStateException("존재하지 않는 사이트 입니다");
		}
		
		// 문제 존재안하면 삽입
		Problem_Site retProblem = null;
		Problem_SitePK problem_SitePK = new Problem_SitePK(
				problem_Site.getProblemSiteName().getProblemSiteName(),
				problem_Site.getProblemNo());
		retProblem = problem_Site_Repository.findById(problem_SitePK).orElse(null);

		if (retProblem == null) {
			problem_Site_Repository.save(problem_Site);
			problem_Site_Repository.flush();
		}
		
		//게시판 상태 추가
		helpme.setHelpmeStatus(Helpme_Class.H00);//수락/거절 대기

		long ret = helpmeRepository.save(helpme).getHelpmeNo();
		return ret;
	}

	//게시글 상세보기
	@Override
	public HelpmeDTO getHelpme(Long helpmeNo) {
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		HelpmeDTO ret = helpmeRepository.getHelpme(helpmeNo, nowLoginMemberNo).orElse(null);
		
		return ret;
	}
	
	//게시글 수정  (문제, 요청 받는사람No, 내용 넘어옴)
	@Override
	public Long updateHelpme(Long helpmeNo, Helpme helpme) {
		//이전 게시글 가져옴
		Helpme preHelpme=helpmeRepository.findById(helpmeNo)
				.orElseThrow(()-> new IllegalStateException("존재하지 않는 게시글입니다"));
		
		Member sendMember = memberRepository
				.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));

		
		
		//동일인이 작성한건지 확인
		if(preHelpme.getHelpmeSenderNo().getNo()!=sendMember.getNo()) {
			throw new IllegalStateException("게시글은 작성자만 수정 할 수 있습니다");
		}

		//본인에게 요청할 경우 못하게 해야함
		if(helpme.getHelpmeReceptorNo().getNo() == sendMember.getNo()) {
			throw new IllegalStateException("자신에게 요청 할 수 없습니다");
		}
		//요청 받는사람 수정
		preHelpme.setHelpmeReceptorNo(helpme.getHelpmeReceptorNo());
		
		//입력받은 문제 받아옴
		Problem_Site problem_Site = helpme.getProblemSite();

		// 존재하는 사이트인지 확인
		Problem_Site_List problem_Site_List = problem_Site_ListRepository.findOne(problem_Site.getProblemSiteName().getProblemSiteName());
		if (problem_Site_List == null) {
			throw new IllegalStateException("존재하지 않는 사이트 입니다");
		}
		
		// 문제 존재안하면 삽입
		Problem_Site retProblem = null;
		Problem_SitePK problem_SitePK = new Problem_SitePK(
				problem_Site.getProblemSiteName().getProblemSiteName(),
				problem_Site.getProblemNo());
		retProblem = problem_Site_Repository.findById(problem_SitePK).orElse(null);

		if (retProblem == null) {
			problem_Site_Repository.save(problem_Site);
			problem_Site_Repository.flush();
		}
		//문제 수정
		preHelpme.setProblemSite(helpme.getProblemSite());
		
		//내용수정
		preHelpme.setHelpmeContent(helpme.getHelpmeContent());
		
		return helpmeNo;
	}
	
	//게시글 삭제
	@Override
	public void deleteHelpme(Long helpmeNo) {
		//게시글 가져옴
		Helpme helpme=helpmeRepository.findById(helpmeNo)
				.orElseThrow(()-> new IllegalStateException("존재하지 않는 게시글입니다"));
		
		Member member = memberRepository
				.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		
		//작성자가 아니면 삭제 불가
		if(helpme.getHelpmeSenderNo().getNo()!= member.getNo()) {
			throw new IllegalStateException("게시글은 작성자만 삭제 할 수 있습니다");
		}
		//OneToMany관계로 cascade처리해줌
		
		
		helpmeRepository.deleteById(helpmeNo);
		
		return;
	}
	
}
