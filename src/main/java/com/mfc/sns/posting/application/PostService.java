package com.mfc.sns.posting.application;

import com.mfc.sns.posting.dto.req.UpdatePostReqDto;
import com.mfc.sns.posting.dto.resp.PostDetailRespDto;

public interface PostService {
	void createPost(String uuid, UpdatePostReqDto dto);
	PostDetailRespDto getPost(Long postId);
}
