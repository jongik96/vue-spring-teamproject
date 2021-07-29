package com.ssafy.common.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.Tuple;

public interface Problem_FollowRepositoryCustom {

	// memberNo가 팔로잉 한 문제(문제 팔로잉)
	 List<Tuple> getProblemFollowings(Long memberNo,Pageable page);
}
