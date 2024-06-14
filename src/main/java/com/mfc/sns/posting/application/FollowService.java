package com.mfc.sns.posting.application;

import org.springframework.data.domain.Pageable;

import com.mfc.sns.posting.dto.req.FollowReqDto;
import com.mfc.sns.posting.dto.resp.FollowListRespDto;

public interface FollowService {
	void createFollow(String userId, FollowReqDto dto);
	void deleteFollow(String userId, FollowReqDto dto);
	boolean isFollowed(String userId, String partnerId);
	FollowListRespDto getFollowList(String userId, Pageable page);
}
