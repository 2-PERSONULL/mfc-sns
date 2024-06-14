package com.mfc.sns.posting.application;

import com.mfc.sns.posting.dto.req.FollowReqDto;
import com.mfc.sns.posting.vo.req.FollowReqVo;

public interface FollowService {
	void createFollow(String userId, FollowReqDto dto);
	void deleteFollow(String userId, FollowReqDto dto);
}
