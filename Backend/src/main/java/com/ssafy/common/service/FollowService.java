package com.ssafy.common.service;

import java.util.Map;

public interface FollowService {

	//멤버 팔로우, 팔로우취소
	Map<String,Object> memberFollow(Long memberNo);
}
