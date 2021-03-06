package com.ssafy.common.repository.problem;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.Tuple;
import com.ssafy.common.domain.problem.Problem_Site;

public interface Problem_FollowRepositoryCustom {

	// memberNo가 팔로잉 한 문제(문제 팔로잉)
	 List<Tuple> getProblemFollowings(Long memberNo,Pageable page);
	 
	//problem으로 현재 팔로잉 몇개인지 카운트
	long countProblemFollowings(String problemSite, long problemNo);
	
	//memberNo가 problem을 팔로우 중인지 아닌지
	boolean isProblemFollowing(long memberNo, Problem_Site problemSite);
}
