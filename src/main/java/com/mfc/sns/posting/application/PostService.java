package com.mfc.sns.posting.application;

import com.mfc.sns.posting.dto.req.UpdatePostReqDto;

public interface PostService {
	void createPost(String uuid, UpdatePostReqDto dto);
}
